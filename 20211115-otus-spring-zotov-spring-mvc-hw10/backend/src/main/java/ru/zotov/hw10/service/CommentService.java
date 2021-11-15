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
     * @return комментарий
     */
    Comment update(Comment comment);

    /**
     * Удалить комментарий
     *
     * @param id ид
     */
    void deleteById(String id);

    /**
     * Поиск комментария по ид
     *
     * @param id ид
     * @return комментарий
     */
    Comment findById(String id);

    /**
     * Поиск комментариев по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    List<Comment> findByBookId(String bookId);

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    List<Comment> findAll();
}
