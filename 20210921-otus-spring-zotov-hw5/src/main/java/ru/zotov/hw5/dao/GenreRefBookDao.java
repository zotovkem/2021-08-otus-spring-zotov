package ru.zotov.hw5.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Created by ZotovES on 11.10.2021
 * репозиторий ссылок книг на жанр
 */
public interface GenreRefBookDao {
    /**
     * Найти все жанры по ид книги
     *
     * @param bookIds список ид книг и жанров
     * @return список жанров
     */
    Map<Long, List<Long>> findByBookIds(Collection<Long> bookIds);

    /**
     * Добавить книге жанр
     *
     * @param bookId  ид книги
     * @param genreId ид автора
     */
    void addRefGenre(Long bookId, Long genreId);

    /**
     * Удалить у книги все ссылки на жанры
     *
     * @param bookId ид книги
     */
    void deleteAllRefGenre(Long bookId);
}
