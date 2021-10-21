package ru.zotov.hw6.dao;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author Created by ZotovES on 11.10.2021
 * Репозитория ссылок книг на авторов
 */
public interface AuthorRefBookDao {

    /**
     * Найти всех авторов по списку ид книг
     *
     * @param bookIds список ид книг и авторов
     * @return список авторов
     */
    Map<Long, List<Long>> findByBookIds(Collection<Long> bookIds);

    /**
     * Добавить книги автора
     *
     * @param bookId   ид книги
     * @param authorId ид жанра
     */
    void addRefAuthor(Long bookId, Long authorId);

    /**
     * Удалить у кинги все ссылки на авторов
     *
     * @param bookId ид книги
     */
    void deleteAllRefAuthor(Long bookId);
}
