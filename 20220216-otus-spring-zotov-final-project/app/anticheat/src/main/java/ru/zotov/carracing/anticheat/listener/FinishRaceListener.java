package ru.zotov.carracing.anticheat.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.zotov.carracing.anticheat.entity.RaceResult;
import ru.zotov.carracing.anticheat.service.CheatReviewService;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.event.RaceFinishEvent;

import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import static ru.zotov.carracing.common.constant.Constants.KAFKA_GROUP_ID;


/**
 * @author Created by ZotovES on 17.08.2021
 * Слушатель событий проверки на атичит
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class FinishRaceListener {
    private final CheatReviewService cheatReviewService;

    @KafkaListener(topics = Constants.KAFKA_RACE_FINISH_TOPIC, groupId = KAFKA_GROUP_ID)
    public void finishRace(RaceFinishEvent finishEvent) throws InterruptedException {
        log.info(String.format("Received event  -> %s", finishEvent));

        Optional.of(finishEvent)
                .map(buildCheatView())
                .ifPresent(cheatReviewService::reviewRaceResult);
    }

    private Function<RaceFinishEvent, RaceResult> buildCheatView() {
        return event -> RaceResult.builder()
                .externalRaceId(UUID.fromString(event.getExternalId()))
                .profileId(UUID.fromString(event.getProfileId()))
                .raceStartTime(event.getStartTime())
                .raceFinishTime(event.getFinishTime())
                .rewardId(event.getRewardId())
                .raceValid(true)
                .build();
    }
}
