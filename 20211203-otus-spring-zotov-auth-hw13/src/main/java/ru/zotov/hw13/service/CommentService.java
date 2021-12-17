package ru.zotov.hw13.service;

import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import ru.zotov.hw13.domain.Comment;

import java.util.List;

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
}
