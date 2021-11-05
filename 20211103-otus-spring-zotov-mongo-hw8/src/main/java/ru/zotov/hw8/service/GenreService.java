package ru.zotov.hw8.service;

import ru.zotov.hw8.domain.Genre;

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
     * Удалить по ид
     *
     * @param id ид жанра
     */
    void deleteById(Long id);

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    List<Genre> findAll();

    /**
     * Найти жанры по наименованию
     *
     * @param genreName наименование жанра
     * @return список жанров
     */
    List<Genre> findByName(String genreName);
}
