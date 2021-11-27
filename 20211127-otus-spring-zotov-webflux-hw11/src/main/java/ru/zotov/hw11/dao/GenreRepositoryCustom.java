package ru.zotov.hw11.dao;

import ru.zotov.hw11.exception.ConstrainDeleteException;

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
    void deleteWithConstraintsByIds(List<String> genreIds) throws ConstrainDeleteException;
}
