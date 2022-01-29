package ru.zotov.hw18.service;

import org.springframework.lang.Nullable;
import ru.zotov.hw18.domain.Comment;

import java.util.List;

/**
 * @author Created by ZotovES on 17.12.2021
 * Сервис комментариев
 */
public interface CommentService {
    /**
     * Сохранить комментарий
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
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    List<Comment> findByAll();

    /**
     * Найти комментарий по ид
     *
     * @param commentId ид комментария
     * @return комментарий
     */
    @Nullable
    Comment findById(Long commentId);

    /**
     * Удалить комментарии по списку ид
     *
     * @param ids список ид комментариев
     */
    void deleteByIds(List<Long> ids);
}
