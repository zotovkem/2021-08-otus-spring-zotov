package ru.zotov.carracing.service;

import ru.zotov.carracing.entity.Race;
import ru.zotov.carracing.entity.RaceTemplate;
import ru.zotov.carracing.enums.RaceState;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 17.08.2021
 * Сервис управления заездами
 */
public interface RaceService {
    /**
     * Найти заезд по ид
     * @param id ид заезда
     * @return заезд
     */
    Optional<Race> findById(Long id);

    /**
     * Создать заезд
     * @param raceTemplate шаблон заезда
     * @return заезд
     */
    Race createRace(RaceTemplate raceTemplate);

    /**
     * Стартовать заезд
     * @param raceId ид заезда
     * @return заезд
     */
    Race start(Long raceId);
    /**
     * Финишировать заезд
     * @param raceId ид заезда
     * @return заезд
     */
    Race finish(Long raceId, String externalId);
    /**
     * Отменить заезд
     * @param raceId ид заезда
     * @return заезд
     */
    Race cancel(Long raceId);

    /**
     * Изменить статус заезда
     * @param state статус заезда
     * @param raceId ид звезда
     */
    void changeState(RaceState state, Long raceId);

    /**
     * Изменить статус заезда
     * @param state статус заезда
     * @param externalRaceId внешний ид заезда
     */
    void changeState(RaceState state, UUID externalRaceId);
}
