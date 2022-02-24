package ru.zotov.carracing.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.entity.Race;
import ru.zotov.carracing.entity.RaceTemplate;
import ru.zotov.carracing.enums.RaceState;
import ru.zotov.carracing.event.FuelExpandEvent;
import ru.zotov.carracing.event.RaceFinishEvent;
import ru.zotov.carracing.event.RewardEvent;
import ru.zotov.carracing.repo.RaceRepo;
import ru.zotov.carracing.security.utils.SecurityService;
import ru.zotov.carracing.service.RaceService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;


/**
 * @author Created by ZotovES on 18.08.2021
 * Реализация сервиса управления заездами
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RaceServiceImpl implements RaceService {
    private final SecurityService securityService;
    private final RaceRepo raceRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * Создать заезд
     *
     * @param raceTemplate шаблон заезда
     * @return заезд
     */
    @Override
    @Transactional(rollbackFor = Exception.class, isolation = Isolation.READ_COMMITTED)
    public Race createRace(RaceTemplate raceTemplate) {
        UUID profileId = UUID.fromString(securityService.getUserTh().getId());
        List<RaceState> raceStates = List.of(RaceState.LOAD, RaceState.LOADED, RaceState.START);
        List<Race> notFinishRaces = raceRepo.findByProfileIdAndStateIn(profileId, raceStates);
        List<Race> loadedRaces = notFinishRaces.stream()
                .filter(r -> RaceState.LOADED.equals(r.getState())).collect(Collectors.toList());

        notFinishRaces.forEach(r -> r.setState(RaceState.CANCEL));
        raceRepo.saveAll(notFinishRaces);

        loadedRaces.stream()
                .map(buildAddFuelMessage(profileId))
                .forEach(fuelExpandEvent -> kafkaTemplate.send(Constants.KAFKA_RACE_START_TOPIC, fuelExpandEvent));

        var race = buildRace(profileId, raceTemplate);
        Race savedRace = raceRepo.save(race);

        kafkaTemplate.send(Constants.KAFKA_RACE_START_TOPIC, buildMessage(profileId, savedRace))
                .addCallback(m -> log.info("Send complete race start"), e -> {
                    throw new KafkaException("Send message error");
                });

        return savedRace;
    }

    /**
     * Стартовать заезд
     *
     * @param raceId ид заезда
     * @return заезд
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race start(Long raceId) {
        Optional<Race> race = raceRepo.findById(raceId);
        return race
                .filter(r -> RaceState.LOADED.equals(r.getState()))
                .map(startRace())
                .map(changeState(RaceState.START))
                .orElse(race.orElseThrow());
    }

    /**
     * Найти заезд по ид
     *
     * @param id ид заезда
     * @return заезд
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Race> findById(Long id) {
        return raceRepo.findById(id);
    }

    /**
     * Финишировать заезд
     *
     * @param raceId ид заезда
     * @return заезд
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race finish(Long raceId, String externalId) {
        UUID profileId = UUID.fromString(securityService.getUserTh().getId());
        UUID externalUuid;
        try {
            externalUuid = UUID.fromString(externalId);
        } catch (Exception e) {
            externalUuid = null;
        }
        Optional<Race> oRace = Optional.ofNullable(externalUuid)
                .flatMap(raceRepo::findByExternalId)
                .filter(r -> Objects.nonNull(raceId))
                .filter(r -> raceId.equals(r.getId()))
                .filter(r -> profileId.equals(r.getProfileId()));

        oRace.filter(r -> RaceState.START.equals(r.getState()))
                .map(changeState(RaceState.FINISH_SUCCESS))
                .ifPresent(race -> {
                    log.info("Отправляем сообщение о выдаче награды");
                    kafkaTemplate.send(Constants.KAFKA_TO_REWARD_TOPIC, buildRewardEvent(race));
                    kafkaTemplate.send(Constants.PROFILE_PROGRESS, buildFinishEvent(race));
                    if (race.getRaceTemplate().getCheckOnCheat()) {
                        log.info("Отправляем результаты заезда на проверку в Античит");
                        kafkaTemplate.send(Constants.KAFKA_RACE_FINISH_TOPIC, buildFinishEvent(race));
                    }
                });

        return oRace.orElseThrow();
    }

    /**
     * Отменить заезд
     *
     * @param raceId ид заезда
     * @return заезд
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Race cancel(Long raceId) {
        UUID profileId = UUID.fromString(securityService.getUserTh().getId());
        Optional<Race> race = raceRepo.findById(raceId)
                .filter(r -> !RaceState.FINISH_SUCCESS.equals(r.getState()));

        race.filter(r -> RaceState.LOADED.equals(r.getState()))
                .map(buildAddFuelMessage(profileId))
                .ifPresent(fuelExpandEvent -> kafkaTemplate.send(Constants.KAFKA_RACE_START_TOPIC, fuelExpandEvent)
                        .addCallback(m -> log.info("Send complete"), e -> {
                            throw new KafkaException("Send message error");
                        }));
        return race.map(changeState(RaceState.CANCEL)).orElseThrow();
    }

    /**
     * Изменить статус заезда
     *
     * @param state  статус заезда
     * @param raceId ид звезда
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeState(RaceState state, Long raceId) {
        Optional<Race> raceOptional = raceRepo.findById(raceId);
        raceOptional.ifPresent(race -> {
            race.setState(state);
            raceRepo.save(race);
        });
    }

    /**
     * Изменить статус заезда
     *
     * @param state          статус заезда
     * @param externalRaceId внешний ид заезда
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void changeState(RaceState state, UUID externalRaceId) {
        Optional<Race> raceOptional = raceRepo.findByExternalId(externalRaceId);
        raceOptional.ifPresent(race -> {
            race.setState(state);
            raceRepo.save(race);
        });
    }

    /**
     * Собрать модель заезда
     *
     * @param profileId    ид профиля игрока
     * @param raceTemplate шаблон заезда
     * @return заезд
     */
    private Race buildRace(UUID profileId, RaceTemplate raceTemplate) {
        return Race.builder()
                .raceTemplate(raceTemplate)
                .profileId(profileId)
                .state(RaceState.LOAD)
                .build();
    }

    /**
     * Старт заезда
     */
    private Function<Race, Race> startRace() {
        return race -> {
            race.setExternalId(UUID.randomUUID());
            race.setRaceStartTime(ZonedDateTime.now().toEpochSecond());
            return race;
        };
    }

    /**
     * Собрать модель результата зазеда
     *
     * @param race заезд
     * @return результат зазеда
     */
    private RaceFinishEvent buildFinishEvent(Race race) {
        return RaceFinishEvent.builder()
                .profileId(race.getProfileId().toString())
                .externalId(requireNonNull(race.getExternalId()).toString())
                .startTime(race.getRaceStartTime())
                .rewardId(race.getRaceTemplate().getRewardId())
                .finishTime(ZonedDateTime.now().toEpochSecond())
                .build();
    }

    /**
     * Изменить состояние зазезда
     *
     * @param raceState новое состояние заезда
     */
    private Function<Race, Race> changeState(RaceState raceState) {
        return race -> {
            race.setState(raceState);
            return raceRepo.save(race);
        };
    }

    /**
     * Собрать модель награды
     *
     * @param race заезд
     * @return награда
     */
    private RewardEvent buildRewardEvent(Race race) {
        return RewardEvent.builder()
                .rewardId(race.getRaceTemplate().getRewardId())
                .profileId(race.getProfileId().toString())
                .build();
    }

    /**
     * Собрать модель сообщения о расходе топлива
     *
     * @param profileId внешний ид профиля игрока
     * @param race      заезд
     * @return информация о расходе топлива
     */
    private FuelExpandEvent buildMessage(UUID profileId, Race race) {
        return FuelExpandEvent.builder()
                .raceId(race.getId())
                .profileId(profileId.toString())
                .fuel(race.getRaceTemplate().getFuelConsume()).build();
    }

    /**
     * Собрать модель сообщения о добавления топлива
     *
     * @param profileId внешний ид профиля игрока
     */
    private Function<Race, FuelExpandEvent> buildAddFuelMessage(UUID profileId) {
        return race -> FuelExpandEvent.builder()
                .raceId(race.getId())
                .profileId(profileId.toString())
                .fuel(-race.getRaceTemplate().getFuelConsume())
                .build();
    }
}
