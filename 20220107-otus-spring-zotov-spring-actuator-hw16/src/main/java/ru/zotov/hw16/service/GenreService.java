package ru.zotov.hw16.service;

import ru.zotov.hw16.domain.Genre;
import ru.zotov.hw16.exception.ConstrainDeleteException;

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
    Genre save(Genre genre);

    /**
     * Получить жанр по ид
     *
     * @param id ид
     * @return жанр
     */
    Optional<Genre> findById(String id);

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    List<Genre> findAll();

    /**
     * Поиск жанров по списку ид
     *
     * @param ids список ид
     * @return список жанров
     */
    Set<Genre> findByIdIn(List<String> ids);

    /**
     * Удалить жанры по списку ид
     *
     * @param ids список ид жанра
     */
    void deleteByListIds(List<String> ids) throws ConstrainDeleteException;
}
