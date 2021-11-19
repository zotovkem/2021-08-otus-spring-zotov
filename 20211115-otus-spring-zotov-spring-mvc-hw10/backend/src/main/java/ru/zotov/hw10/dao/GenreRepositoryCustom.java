package ru.zotov.hw10.dao;

import java.util.List;

/**
 * @author Created by ZotovES on 11.11.2021
 * Кастомный репозиторий жанров
 */
public interface GenreRepositoryCustom {
    /**
     * Удаление с проверкой зависимых сущностей
     *
     * @param genreIds список ид жанров
     */
    void deleteWithConstraintsByIds(List<String> genreIds);
}
