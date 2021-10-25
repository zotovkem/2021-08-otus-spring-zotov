package ru.zotov.hw7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw7.dao.CommentRepository;
import ru.zotov.hw7.domain.Comment;
import ru.zotov.hw7.service.CommentService;

import java.util.List;

/**
 * @author Created by ZotovES on 25.10.2021
 * Реализация сервиса управления комментариями
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

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
     * Поиск комментария по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    @Override
    @Transactional(readOnly = true)
    public List<Comment> findByBookId(Long bookId) {
        return commentRepository.findByBookId(bookId);
    }
}
