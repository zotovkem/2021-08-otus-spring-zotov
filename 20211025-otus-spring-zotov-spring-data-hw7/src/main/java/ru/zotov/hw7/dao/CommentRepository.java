package ru.zotov.hw7.dao;

import ru.zotov.hw7.domain.Comment;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 22.10.2021
 * Репозиторий комментариев
 */
public interface CommentRepository {
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
    Optional<Comment> findById(Long id);

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    List<Comment> findAll();

    /**
     * Получить список комментариев по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    List<Comment> findByBookId(Long bookId);
}
