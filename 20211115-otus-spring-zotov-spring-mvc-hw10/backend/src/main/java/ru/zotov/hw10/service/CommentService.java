package ru.zotov.hw10.service;

import ru.zotov.hw10.domain.Comment;

import java.util.List;

/**
 * @author Created by ZotovES on 25.10.2021
 * Сервис управления комментариями
 */
public interface CommentService {
    /**
     * Создать комментарий
     *
     * @param comment комментарий
     * @param bookId  ид книги
     * @return комментарий
     */
    Comment create(Comment comment, String bookId);

    /**
     * Редактировать комментарий
     *
     * @param comment комментарий
     * @param bookId  ид книги
     * @return комментарий
     */
    Comment update(Comment comment, String bookId);

    /**
     * Удалить комментарий
     *
     * @param ids    список ид
     * @param bookId ид книги
     */
    void deleteByIds(List<String> ids, String bookId);

    /**
     * Поиск комментариев по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    List<Comment> findByBookId(String bookId);

    Comment findById(String id, String bookId);

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    List<Comment> findAll();
}
