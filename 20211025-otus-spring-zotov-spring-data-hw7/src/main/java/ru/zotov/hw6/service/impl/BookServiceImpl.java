package ru.zotov.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw6.dao.BookRepository;
import ru.zotov.hw6.domain.Book;
import ru.zotov.hw6.service.BookService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 07.10.2021
 * Реализация сервиса управления книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookDao;

    /**
     * Создать книгу
     *
     * @param book книга
     * @return книга
     */
    @Override
    @Transactional
    public Book create(Book book) {
        return bookDao.create(book);
    }

    /**
     * Редактировать книгу
     *
     * @param book Книга
     * @return книга
     */
    @Override
    @Transactional
    public Book update(Book book) {
        return bookDao.update(book);
    }

    /**
     * Удалить книгу по ид
     *
     * @param id ид
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        bookDao.deleteById(id);
    }

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAll() {
        List<Book> all = bookDao.findAll();
        loadLazyFields(all);

        return all;
    }

    /**
     * Найти книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long id) {
        return bookDao.findById(id);
    }

    /**
     * Найти книги по наименованию
     *
     * @param name наименование
     * @return список книг
     */
    @Override
    @Transactional(readOnly = true)
    public List<Book> findByName(String name) {
        return bookDao.findByName(name);
    }

    /**
     * Найти книги по жанру
     *
     * @param name наименование жанра
     * @return список книг
     */
    @Override
    @Transactional(readOnly = true)
    public List<Book> findByGenreName(String name) {
        List<Book> books = bookDao.findByGenreName(name);
        loadLazyFields(books);
        return books;
    }

    /**
     * Поиск книг по фио автора
     *
     * @param fio фио автора
     * @return список книг
     */
    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAuthorFio(String fio) {
        List<Book> books = bookDao.findByAuthorFio(fio);
        loadLazyFields(books);
        return books;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void loadLazyFields(List<Book> all) {
        all.stream()
                .map(Book::getGenres)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        all.stream()
                .map(Book::getAuthors)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }
}
