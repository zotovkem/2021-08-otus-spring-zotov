package ru.zotov.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw6.dao.CommentRepository;
import ru.zotov.hw6.domain.Book;
import ru.zotov.hw6.domain.Comment;
import ru.zotov.hw6.service.BookService;
import ru.zotov.hw6.service.CommentService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 25.10.2021
 * Реализация сервиса управления комментариями
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final BookService bookService;

    /**
     * Создать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    @Transactional
    public Comment create(Comment comment) {
        return commentRepository.create(comment);
    }

    /**
     * Редактировать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    @Transactional
    public Comment update(Comment comment) {
        Comment persistComment = commentRepository.findById(comment.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id"));
        persistComment.setContent(comment.getContent());
        persistComment.setAuthor(comment.getAuthor());

        return commentRepository.update(persistComment);
    }

    /**
     * Удалить комментарий
     *
     * @param id ид
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        commentRepository.deleteById(id);
    }

    /**
     * Поиск комментария по ид
     *
     * @param id ид
     * @return комментарий
     */
    @Override
    @Transactional(readOnly = true)
    public Comment findById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id"));
    }

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    /**
     * Поиск комментариев по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBookId(Long bookId) {
        List<Comment> comments = bookService.findById(bookId)
                .map(Book::getComments)
                .orElse(Collections.emptyList());
        loadLazyFields(comments);

        return comments;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadLazyFields(List<Comment> comments) {
        comments.stream()
                .map(Comment::getContent)
                .collect(Collectors.toList());
    }
}
