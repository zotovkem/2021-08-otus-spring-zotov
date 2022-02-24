package ru.zotov.carracing.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.util.concurrent.ListenableFuture;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.entity.Race;
import ru.zotov.carracing.entity.RaceTemplate;
import ru.zotov.carracing.enums.RaceState;
import ru.zotov.carracing.event.FuelExpandEvent;
import ru.zotov.carracing.event.RaceFinishEvent;
import ru.zotov.carracing.event.RewardEvent;
import ru.zotov.carracing.repo.RaceRepo;
import ru.zotov.carracing.security.filter.CustomUser;
import ru.zotov.carracing.security.utils.SecurityService;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 23.02.2022
 */
@DisplayName("Тестирование сервиса заезда")
@ExtendWith(MockitoExtension.class)
class RaceServiceImplTest {
    @Mock private SecurityService securityService;
    @Mock private RaceRepo raceRepo;
    @Mock private KafkaTemplate<String, Object> kafkaTemplate;
    @InjectMocks RaceServiceImpl raceService;

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Создание заезда")
    void createRaceTest() {
        when(securityService.getUserTh()).thenReturn(new CustomUser("62e9a377-6078-488f-bb4c-3de998293086", "testUser", "pass",
                Collections.emptyList()));
        when(raceRepo.findByProfileIdAndStateIn(any(), anyList())).thenReturn(Collections.emptyList());
        when(raceRepo.saveAll(anyList())).thenAnswer(invocation -> invocation.getArgument(0));
        when(raceRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(kafkaTemplate.send(eq(Constants.KAFKA_RACE_START_TOPIC), any(FuelExpandEvent.class)))
                .thenReturn(mock(ListenableFuture.class));

        Race result = raceService.createRace(RaceTemplate.builder().build());

        assertThat(result).isNotNull();

        verify(securityService).getUserTh();
        verify(raceRepo).findByProfileIdAndStateIn(any(), anyList());
        verify(raceRepo).saveAll(anyList());
        verify(raceRepo).save(any());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_RACE_START_TOPIC), any(FuelExpandEvent.class));
    }

    @Test
    @DisplayName("Старт заезда")
    void startTest() {
        Race race = Race.builder()
                .id(1L)
                .state(RaceState.LOADED)
                .raceTemplate(RaceTemplate.builder().build())
                .build();
        when(raceRepo.findById(anyLong())).thenReturn(Optional.of(race));
        when(raceRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Race result = raceService.start(1L);

        assertThat(result).isNotNull().extracting("state").isEqualTo(RaceState.START);

        verify(raceRepo).findById(anyLong());
        verify(raceRepo).save(any());
    }

    @Test
    @DisplayName("Старт не загруженного заезда")
    void startNotLoadedRaceTest() {
        Race race = Race.builder()
                .id(1L)
                .state(RaceState.LOAD)
                .raceTemplate(RaceTemplate.builder().build())
                .build();
        when(raceRepo.findById(anyLong())).thenReturn(Optional.of(race));

        Race result = raceService.start(1L);

        assertThat(result).isNotNull().extracting("state").isEqualTo(RaceState.LOAD);

        verify(raceRepo).findById(anyLong());
        verify(raceRepo,never()).save(any());
    }

    @Test
    @DisplayName("Поиск заезда по ид")
    void findByIdTest() {
        when(raceRepo.findById(anyLong())).thenReturn(Optional.of(Race.builder().build()));

        Optional<Race> result = raceService.findById(1L);

        assertThat(result).isPresent();

        verify(raceRepo).findById(anyLong());
    }

    @Test
    @DisplayName("Финиш зазеда")
    void finishTest() {
        UUID uuid = UUID.randomUUID();
        RaceTemplate raceTemplate = RaceTemplate.builder()
                .checkOnCheat(true)
                .build();
        Race race = Race.builder()
                .id(1L)
                .externalId(uuid)
                .state(RaceState.START)
                .raceTemplate(raceTemplate)
                .profileId(uuid)
                .build();
        when(securityService.getUserTh()).thenReturn(new CustomUser(uuid.toString(), "testUser", "pass",
                Collections.emptyList()));
        when(raceRepo.findByExternalId(any())).thenReturn(Optional.of(race));
        when(raceRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Race result = raceService.finish(1L, "62e9a377-6078-488f-bb4c-3de998293086");

        assertThat(result).isNotNull().extracting("state").isEqualTo(RaceState.FINISH_SUCCESS);

        verify(securityService).getUserTh();
        verify(raceRepo).findByExternalId(any());
        verify(raceRepo).save(any());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_TO_REWARD_TOPIC), any(RewardEvent.class));
        verify(kafkaTemplate).send(eq(Constants.PROFILE_PROGRESS), any(RaceFinishEvent.class));
        verify(kafkaTemplate).send(eq(Constants.KAFKA_RACE_FINISH_TOPIC), any(RaceFinishEvent.class));
    }

    @SuppressWarnings("unchecked")
    @Test
    @DisplayName("Отмена заезда")
    void cancelTest() {
        UUID uuid = UUID.randomUUID();
        Race race = Race.builder()
                .id(1L)
                .state(RaceState.LOADED)
                .raceTemplate(RaceTemplate.builder()
                        .fuelConsume(1)
                        .build())
                .profileId(uuid)
                .build();

        when(securityService.getUserTh()).thenReturn(new CustomUser(uuid.toString(), "testUser", "pass",
                Collections.emptyList()));
        when(raceRepo.findById(anyLong())).thenReturn(Optional.of(race));
        when(raceRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(kafkaTemplate.send(eq(Constants.KAFKA_RACE_START_TOPIC), any(FuelExpandEvent.class)))
                .thenReturn(mock(ListenableFuture.class));

        Race result = raceService.cancel(1L);

        assertThat(result).isNotNull().extracting("state").isEqualTo(RaceState.CANCEL);

        verify(securityService).getUserTh();
        verify(raceRepo).findById(anyLong());
        verify(raceRepo).save(any());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_RACE_START_TOPIC), any(FuelExpandEvent.class));
    }

    @Test
    @DisplayName("Изменить состояние заезда")
    void changeStateTest() {
        Race race = Race.builder()
                .id(1L)
                .state(RaceState.LOADED)
                .raceTemplate(RaceTemplate.builder().build())
                .build();

        when(raceRepo.findById(anyLong())).thenReturn(Optional.of(race));

        raceService.changeState(RaceState.LOAD,1L);

        verify(raceRepo).findById(anyLong());
        verify(raceRepo).save(any());
    }

    @Test
    @DisplayName("Изменить состояние заезда по внешнему ид заезда")
    void changeStateByExternalIdTest() {
        Race race = Race.builder()
                .id(1L)
                .state(RaceState.LOADED)
                .raceTemplate(RaceTemplate.builder().build())
                .build();

        when(raceRepo.findByExternalId(any())).thenReturn(Optional.of(race));

        raceService.changeState(RaceState.LOAD, UUID.randomUUID());

        verify(raceRepo).findByExternalId(any());
        verify(raceRepo).save(any());
    }
}
