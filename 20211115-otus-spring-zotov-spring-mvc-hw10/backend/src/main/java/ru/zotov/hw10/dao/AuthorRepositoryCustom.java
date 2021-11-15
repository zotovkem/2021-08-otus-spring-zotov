package ru.zotov.hw10.dao;

/**
 * @author Created by ZotovES on 11.11.2021
 * Кастомный репозиторий авторов
 */
public interface AuthorRepositoryCustom {
    /**
     * Удаление с проверкой зависимых сущностей
     *
     * @param id ид автора
     */
    void deleteWithConstraintsById(String id);
}
