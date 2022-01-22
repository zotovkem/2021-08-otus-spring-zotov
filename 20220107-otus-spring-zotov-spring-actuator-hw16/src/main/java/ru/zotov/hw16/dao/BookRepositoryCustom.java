package ru.zotov.hw16.dao;

import java.util.List;

/**
 * @author Created by ZotovES on 08.11.2021
 * Кастомный репозиторий книг
 */
public interface BookRepositoryCustom {
    /**
     * Удаление книг по списку ид
     *
     * @param ids список ид книг
     */
    void deleteByIds(List<String> ids);
}
