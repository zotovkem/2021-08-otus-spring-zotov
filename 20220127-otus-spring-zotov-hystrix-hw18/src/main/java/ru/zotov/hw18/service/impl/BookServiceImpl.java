package ru.zotov.hw18.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw18.dao.BookRepository;
import ru.zotov.hw18.domain.Book;
import ru.zotov.hw18.service.BookService;

import java.util.List;

/**
 * @author Created by ZotovES on 07.10.2021
 * Реализация сервиса управления книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookDao;

    /**
     * Редактировать книгу
     *
     * @param book Книга
     * @return книга
     */
    @Override
    public Book update(Book book) {
        return bookDao.save(book);
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
        book.setId(null);
        Book savedBook = bookDao.save(book);

        return savedBook;
    }

    /**
     * Удалить книгу по ид
     *
     * @param ids список ид
     */
    @Override
    @Transactional
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
    @Nullable
    public Book findById(Long id) {
        return bookDao.findById(id).orElse(null);
    }
}
