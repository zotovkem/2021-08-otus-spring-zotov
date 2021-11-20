package ru.zotov.hw10.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw10.dao.CommentRepository;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Comment;
import ru.zotov.hw10.service.BookService;
import ru.zotov.hw10.service.CommentService;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

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
        Book book = getBookById(bookId);

        comment.setId(UUID.randomUUID().toString());
        book.getComments().add(comment);
        bookService.save(book);

        return comment;
    }

    /**
     * Редактировать комментарий
     *
     * @param comment комментарий
     * @param bookId  ид книги
     * @return комментарий
     */
    @Override
    @Transactional
    public Comment update(Comment comment, String bookId) {
        Book book = getBookById(bookId);

        List<Comment> commentList = book.getComments().stream()
                .filter(c -> nonNull(c.getId()) && c.getId().equals(comment.getId()))
                .collect(Collectors.toList());
        commentList.add(comment);
        book.setComments(commentList);

        bookService.save(book);

        return comment;
    }

    /**
     * Удалить комментарий
     *
     * @param ids    список ид
     * @param bookId ид книги
     */
    @Override
    @Transactional
    public void deleteByIds(List<String> ids, String bookId) {
        Book book = getBookById(bookId);

        List<Comment> commentList = book.getComments().stream()
                .filter(c -> nonNull(c.getId()) && ids.contains(c.getId()))
                .collect(Collectors.toList());
        book.setComments(commentList);

        bookService.save(book);
    }

    /**
     * Поиск комментария по ид
     *
     * @param id     ид
     * @param bookId ид книги
     * @return комментарий
     */
    @Override
    public Comment findById(String id, String bookId) {
        return bookService.findById(bookId)
                .map(Book::getComments).stream()
                .flatMap(Collection::stream)
                .filter(c -> nonNull(c.getId()) && c.getId().equals(id))
                .findFirst()
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

    /**
     * Получить книгу по ид
     *
     * @param bookId ид книги
     * @return книга
     */
    private Book getBookById(String bookId) {
        return bookService.findById(bookId).orElseThrow(() -> {
            throw new IllegalArgumentException("Invalid book id");
        });
    }
}
