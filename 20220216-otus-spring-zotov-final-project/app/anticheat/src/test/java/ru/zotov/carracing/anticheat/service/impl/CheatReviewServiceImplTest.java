package ru.zotov.carracing.anticheat.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import ru.zotov.carracing.anticheat.entity.RaceResult;
import ru.zotov.carracing.anticheat.repo.CheatReviewRepo;
import ru.zotov.carracing.common.constant.Constants;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * @author Created by ZotovES on 18.02.2022
 */
@DisplayName("Тестирование сервиса проверки заезда на античит")
@ExtendWith(MockitoExtension.class)
class CheatReviewServiceImplTest {
    @Mock private CheatReviewRepo cheatReviewRepo;
    @Mock private KafkaTemplate<String, Object> kafkaTemplate;
    @InjectMocks private CheatReviewServiceImpl cheatReviewService;

    @Test
    @DisplayName("Не успешная проверка заезда на атичит")
    void failReviewRaceResultTest() {
        UUID uuid = UUID.randomUUID();
        RaceResult raceResult = RaceResult.builder()
                .externalRaceId(uuid)
                .profileId(uuid)
                .raceStartTime(ZonedDateTime.now().minusSeconds(20).toEpochSecond())
                .raceFinishTime(ZonedDateTime.now().toEpochSecond())
                .rewardId(1L)
                .raceValid(true)
                .build();

        cheatReviewService.reviewRaceResult(raceResult);

        verify(kafkaTemplate).send(eq(Constants.KAFKA_RETURN_REWARD_TOPIC), any());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_FAIL_CHEAT_REVIEW_TOPIC), any());
        verify(kafkaTemplate).send(eq(Constants.PROFILE_REGRESS), any());
        verify(cheatReviewRepo).save(any());
    }

    @Test
    @DisplayName("Успешная проверка заезда на атичит")
    void successReviewRaceResultTest() {
        UUID uuid = UUID.randomUUID();
        RaceResult raceResult = RaceResult.builder()
                .externalRaceId(uuid)
                .profileId(uuid)
                .raceStartTime(ZonedDateTime.now().minusMinutes(2).toEpochSecond())
                .raceFinishTime(ZonedDateTime.now().toEpochSecond())
                .rewardId(1L)
                .raceValid(true)
                .build();

        cheatReviewService.reviewRaceResult(raceResult);

        verify(kafkaTemplate, never()).send(any(), any());
        verify(cheatReviewRepo).save(any());
    }
}
