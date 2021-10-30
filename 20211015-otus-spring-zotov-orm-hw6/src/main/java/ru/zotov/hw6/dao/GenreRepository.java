package ru.zotov.hw6.dao;

import ru.zotov.hw6.domain.Genre;

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
     * @param genre жанр
     */
    void delete(Genre genre);

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
