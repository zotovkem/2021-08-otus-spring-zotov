package ru.zotov.hw7.dao;

import ru.zotov.hw7.domain.Genre;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Жанров книг
 */
public interface GenreRepository {
    /**
     * Создать
     *
     * @param genre жанр
     * @return жанр
     */
    Genre create(Genre genre);

    /**
     * Редактировать
     *
     * @param genre жанр
     * @return жанр
     */
    Genre update(Genre genre);

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
}