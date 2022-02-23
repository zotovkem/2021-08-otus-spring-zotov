package ru.zotov.carracing.profile.service;

import ru.zotov.carracing.profile.entity.Profile;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 14.09.2021
 * Сервис управления профилем игрока
 */
public interface ProfileService {
    /**
     * Создание игрока
     *
     * @param externalId внешний ид
     * @return профиль
     */
    Profile create(UUID externalId);

    /**
     * Получить текущий профиль
     *
     * @return профиль
     */
    Optional<Profile> getCurrentProfile();

    /**
     * Добавить игровых очков в профиль
     *
     * @param externalId внешний ид
     * @param score      кол-во очков
     */
    void progressIncrement(UUID externalId, Long score);

    /**
     * Убавить игровых очков в профиль
     *
     * @param externalId внешний ид
     * @param score      кол-во очков
     */
    void regressProgress(UUID externalId, Long score);
}
