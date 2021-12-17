package ru.zotov.hw13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw13.dao.BookRepository;
import ru.zotov.hw13.domain.Book;
import ru.zotov.hw13.domain.Comment;
import ru.zotov.hw13.service.BookService;
import ru.zotov.hw13.service.CommentService;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 07.10.2021
 * Реализация сервиса управления книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookDao;
    private final CommentService commentService;

    /**
     * Редактировать книгу
     *
     * @param book Книга
     * @return книга
     */
    @Override
    @Transactional
    public Book update(Book book) {
        List<Comment> savedComments = commentService.saveListComments(book.getComments(), book.getId());
        Book savedBook = bookDao.save(book);
        savedBook.setComments(savedComments);

        return savedBook;
    }

    /**
     * Создать книгу
     *
     * @param book Книга
     * @return книга
     */
    @Override
    @Transactional
    public Book create(Book book) {
        Book savedBook = bookDao.save(book);

        List<Comment> savedComments = commentService.saveListComments(book.getComments(), savedBook.getId());
        savedBook.setComments(savedComments);

        return savedBook;
    }


    /**
     * Удалить книгу по ид
     *
     * @param ids список ид
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        bookDao.deleteAllByIdInBatch(ids);
    }

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    @Override
    public List<Book> findByAll() {
        return bookDao.findAll();
    }

    /**
     * Найти книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(bookDao.getById(id));
    }
}
