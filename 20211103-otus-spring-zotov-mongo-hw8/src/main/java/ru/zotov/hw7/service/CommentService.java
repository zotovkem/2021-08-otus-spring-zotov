package ru.zotov.hw7.service;

import ru.zotov.hw7.domain.Comment;

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
     * @return комментарий
     */
    Comment create(Comment comment);

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
    void deleteById(Long id);

    /**
     * Поиск комментария по ид
     *
     * @param id ид
     * @return комментарий
     */
    Comment findById(Long id);

    /**
     * Поиск комментариев по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    List<Comment> findByBookId(Long bookId);

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    List<Comment> findAll();
}
