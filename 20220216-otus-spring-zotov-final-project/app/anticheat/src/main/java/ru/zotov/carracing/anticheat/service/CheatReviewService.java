package ru.zotov.carracing.anticheat.service;

import org.springframework.lang.NonNull;
import ru.zotov.carracing.anticheat.entity.RaceResult;

/**
 * @author Created by ZotovES on 03.09.2021
 * Сервис проверки игрока на читерство
 */
public interface CheatReviewService {
    /**
     * Проверка заезда на читерство
     * @param raceResult результат заезда для проверки
     */
    void reviewRaceResult(@NonNull RaceResult raceResult);
}
