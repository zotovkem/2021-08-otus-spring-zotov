package ru.zotov.hw13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw13.dao.CommentRepository;
import ru.zotov.hw13.domain.Book;
import ru.zotov.hw13.domain.Comment;
import ru.zotov.hw13.service.CommentService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 17.12.2021
 * Реализация сервиса коментариев
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    /**
     * Сохранить список комментариев
     *
     * @param incomingBookComments список комментариев
     * @param bookId               ид книги
     * @return список сохраненных комментариев
     */
    @Override
    @Transactional
    public List<Comment> saveListComments(@NonNull List<Comment> incomingBookComments, @NonNull Long bookId) {
        incomingBookComments.forEach(comment -> {
            Book book = Book.builder().id(bookId).build();
            comment.setBook(book);
            if (comment.getCreateDate() == null) {
                comment.setCreateDate(ZonedDateTime.now());
            }
        });

        List<Long> commentsIds = incomingBookComments.stream().map(Comment::getId).collect(Collectors.toList());
        List<Long> deletedCommentIds = incomingBookComments.stream()
                .map(Comment::getId)
                .filter(id -> !commentsIds.contains(id))
                .collect(Collectors.toList());
        if (!deletedCommentIds.isEmpty()) {
            commentRepository.deleteAllByIdInBatch(deletedCommentIds);
        }

        return commentRepository.saveAll(incomingBookComments);
    }
}

