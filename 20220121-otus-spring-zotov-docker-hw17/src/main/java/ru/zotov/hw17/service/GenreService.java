package ru.zotov.hw17.service;

import ru.zotov.hw17.domain.Genre;

import java.util.List;
import java.util.Optional;

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
    Optional<Genre> findById(Long id);

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
    List<Genre> findByIdIn(List<Long> ids);

    /**
     * Удалить жанры по списку ид
     *
     * @param ids список ид жанра
     */
    void deleteByListIds(List<Long> ids);
}
