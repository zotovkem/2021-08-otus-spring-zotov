package ru.zotov.hw6.dao;

import ru.zotov.hw6.domain.Comment;

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
     * @param comment комментарий
     */
    void delete(Comment comment);

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
}
