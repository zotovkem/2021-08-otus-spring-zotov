package ru.zotov.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw8.dao.CommentRepository;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Comment;
import ru.zotov.hw8.service.BookService;
import ru.zotov.hw8.service.CommentService;

import java.util.List;
import java.util.Optional;

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
    public Comment create(Comment comment) {
        Optional<Book> book = bookService.findById(comment.getBook().getId());

        book.ifPresentOrElse(comment::setBook, () -> {
            throw new IllegalArgumentException("Invalid book id");
        });

        return commentRepository.save(comment);
    }

    /**
     * Редактировать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    public Comment update(Comment comment) {
        Comment persistComment = commentRepository.findById(comment.getId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid comment id"));
        persistComment.setContent(comment.getContent());
        persistComment.setAuthor(comment.getAuthor());

        return commentRepository.save(persistComment);
    }

    /**
     * Удалить комментарий
     *
     * @param id ид
     */
    @Override
    public void deleteById(String id) {
        commentRepository.deleteById(id);
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
        return commentRepository.findByBookId(bookId);
    }
}
