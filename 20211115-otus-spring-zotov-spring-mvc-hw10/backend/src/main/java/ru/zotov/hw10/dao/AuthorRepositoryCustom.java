package ru.zotov.hw10.dao;

import java.util.List;

/**
 * @author Created by ZotovES on 11.11.2021
 * Кастомный репозиторий авторов
 */
public interface AuthorRepositoryCustom {
    /**
     * Удаление авторов с проверкой зависимых сущностей
     *
     * @param ids список ид авторов
     */
    void deleteWithConstraintsByIds(List<String> ids);
}
