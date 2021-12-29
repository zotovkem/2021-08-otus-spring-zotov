package ru.zotov.hw14.service;

import ru.zotov.hw14.domain.GenreMongo;
import ru.zotov.hw14.exception.ConstrainDeleteException;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Created by ZotovES on 28.10.2021
 * Сервис жанров
 */
public interface GenreService {
    /**
     * Сохранить
     *
     * @param genre жанр
     * @return жанр
     */
    GenreMongo save(GenreMongo genre);

    /**
     * Получить жанр по ид
     *
     * @param id ид
     * @return жанр
     */
    Optional<GenreMongo> findById(String id);

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    List<GenreMongo> findAll();

    /**
     * Поиск жанров по списку ид
     *
     * @param ids список ид
     * @return список жанров
     */
    Set<GenreMongo> findByIdIn(List<String> ids);

    /**
     * Удалить жанры по списку ид
     *
     * @param ids список ид жанра
     */
    void deleteByListIds(List<String> ids) throws ConstrainDeleteException;
}
