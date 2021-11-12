package ru.zotov.hw8.dao;

import ru.zotov.hw8.domain.Comment;

/**
 * @author Created by ZotovES on 12.11.2021
 * Кастомный репозиторий комментариев к книгам
 */
public interface CommentRepositoryCustom {
    /**
     * Каскадное обновление комментария
     *
     * @param comment комментарий
     * @return комментарий
     */
    Comment cascadeSave(Comment comment);

    /**
     * Каскадное удаление комментария
     *
     * @param commentId ид комментария
     */
    void cascadeDelete(String commentId);
}
