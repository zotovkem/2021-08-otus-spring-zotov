package ru.zotov.hw5.dao;

import ru.zotov.hw5.domain.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Жанров книг
 */
public interface GenreDao {
    /**
     * Создать
     *
     * @param genre жанр
     */
    void create(Genre genre);

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
    Optional<Genre> getById(Long id);

    /**
     * Удалить по ид
     *
     * @param id ид жанра
     */
    void deleteById(Long id);

    /**
     * Найти по списку идентификаторов
     *
     * @param ids список ид
     * @return список жанров
     */
    List<Genre> findByIdsIn(Collection<Long> ids);

    /**
     * Найти все жанры по ид книги
     *
     * @param bookIds список ид книг
     * @return список жанров
     */
    Map<Long, List<Genre>> findByBookIds(Collection<Long> bookIds);

    /**
     * Найти жанры книги по ид книги
     *
     * @param bookId ид книги
     * @return список жанров
     */
    List<Genre> findByBookId(Long bookId);

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    List<Genre> getAll();
}
