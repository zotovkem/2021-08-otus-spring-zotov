package ru.zotov.hw8.dao;

/**
 * @author Created by ZotovES on 11.11.2021
 * Кастомный репозиторий жанров
 */
public interface GenreRepositoryCustom {
    /**
     * Удаление с проверкой зависимых сущностей
     *
     * @param genreId ид жанра
     */
    void deleteWithConstraintsById(String genreId);
}
