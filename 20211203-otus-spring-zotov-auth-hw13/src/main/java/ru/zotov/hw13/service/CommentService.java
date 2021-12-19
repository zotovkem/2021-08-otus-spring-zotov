package ru.zotov.hw13.service;

import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.zotov.hw13.domain.Comment;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 17.12.2021
 * Сервис комментариев
 */
public interface CommentService {
    /**
     * Сохранить список комментариев
     *
     * @param incomingBookComments список комментариев
     * @param bookId               ид книги
     * @return список сохраненных комментариев
     */
    @PreAuthorize("hasAnyRole('ADMIN','ADULT')")
    List<Comment> saveListComments(@NonNull List<Comment> incomingBookComments, @NonNull Long bookId);

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
    Optional<Comment> findById(Long commentId);

    /**
     * Удалить комментарии по списку ид
     *
     * @param ids список ид комментариев
     */
    void deleteByIds(List<Long> ids);
}
