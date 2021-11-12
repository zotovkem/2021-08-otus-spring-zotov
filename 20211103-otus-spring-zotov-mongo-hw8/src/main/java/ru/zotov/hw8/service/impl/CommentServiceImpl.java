package ru.zotov.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw8.dao.CommentRepository;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Comment;
import ru.zotov.hw8.service.BookService;
import ru.zotov.hw8.service.CommentService;

import java.util.Collections;
import java.util.List;

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
    public Comment create(Comment comment, String bookId) {
        Book book = bookService.findById(bookId).orElseThrow(() -> {
            throw new IllegalArgumentException("Invalid book id");
        });

        Comment savedComment = commentRepository.save(comment);
        book.getComments().add(savedComment);
        bookService.save(book);

        return savedComment;
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
        return commentRepository.cascadeSave(comment);
    }

    /**
     * Удалить комментарий
     *
     * @param id ид
     */
    @Override
    @Transactional
    public void deleteById(String id) {
        commentRepository.cascadeDelete(id);
    }

    /**
     * Поиск комментария по ид
     *
     * @param id ид
     * @return комментарий
     */
    @Override
    public Comment findById(String id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id"));
    }

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    @Override
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
    public List<Comment> findByBookId(String bookId) {
        return bookService.findById(bookId)
                .map(Book::getComments)
                .orElse(Collections.emptyList());
    }
}
