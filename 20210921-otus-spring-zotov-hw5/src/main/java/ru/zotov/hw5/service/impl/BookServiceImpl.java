package ru.zotov.hw5.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw5.dao.AuthorDao;
import ru.zotov.hw5.dao.BookDao;
import ru.zotov.hw5.dao.GenreDao;
import ru.zotov.hw5.domain.Author;
import ru.zotov.hw5.domain.Book;
import ru.zotov.hw5.domain.Genre;
import ru.zotov.hw5.service.BookService;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 07.10.2021
 * Реализация сервиса управления книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final AuthorDao authorDao;
    private final GenreDao genreDao;

    /**
     * Создать книгу
     *
     * @param book книга
     */
    @Override
    public void create(Book book) {
        Book savedBook = bookDao.create(book);
        book.getAuthors().forEach(author -> bookDao.addAuthor(savedBook.getId(), author.getId()));
        book.getGenres().forEach(genre -> bookDao.addGenre(savedBook.getId(), genre.getId()));
    }

    /**
     * Редактировать книгу
     *
     * @param book Книга
     * @return книга
     */
    @Override
    public Book update(Book book) {
        bookDao.update(book);
        bookDao.deleteAllRefAuthor(book.getId());
        bookDao.deleteAllRefGenre(book.getId());
        book.getAuthors().forEach(author -> bookDao.addAuthor(book.getId(), author.getId()));
        book.getGenres().forEach(genre -> bookDao.addGenre(book.getId(), genre.getId()));

        return findById(book.getId()).orElseThrow(() -> new IllegalArgumentException("Not found book by id = " + book.getId()));
    }

    /**
     * Удалить книгу по ид
     *
     * @param id ид
     */
    @Override
    public void deleteById(Long id) {
        bookDao.deleteById(id);
    }

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    @Override
    public List<Book> findByAll() {
        List<Book> books = bookDao.findAll();
        setBookFieldsCollection(books);

        return books;
    }

    /**
     * Найти книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @Override
    public Optional<Book> findById(Long id) {
        Optional<Book> book = bookDao.getById(id);
        book.ifPresent(setBookFieldsConsumer());

        return book;
    }

    /**
     * Найти книги по наименованию
     *
     * @param name наименование
     * @return список книг
     */
    @Override
    public List<Book> findByName(String name) {
        List<Book> books = bookDao.findByName(name);
        setBookFieldsCollection(books);

        return books;
    }

    /**
     * Найти книги по жанру
     *
     * @param name наименование жанра
     * @return список книг
     */
    @Override
    public List<Book> findByGenreName(String name) {
        List<Book> books = bookDao.findByGenreName(name);
        setBookFieldsCollection(books);

        return books;
    }

    /**
     * Поиск книг по фио автора
     *
     * @param fio фио автора
     * @return список книг
     */
    @Override
    public List<Book> findByAuthorFio(String fio) {
        List<Book> books = bookDao.findByAuthorFio(fio);
        setBookFieldsCollection(books);

        return books;
    }

    /**
     * Консюмер проставляет значения в поля коллекций книги
     */
    private Consumer<Book> setBookFieldsConsumer() {
        return b -> {
            List<Author> authors = authorDao.findByBookIds(List.of(b.getId())).getOrDefault(b.getId(), Collections.emptyList());
            List<Genre> genres = genreDao.findByBookIds(List.of(b.getId())).getOrDefault(b.getId(), Collections.emptyList());
            b.setAuthors(authors);
            b.setGenres(genres);
        };
    }

    /**
     * Проставляет в поля коллекция книги значения
     *
     * @param books список книг
     */
    private void setBookFieldsCollection(List<Book> books) {
        Set<Long> bookIds = books.stream().map(Book::getId).collect(Collectors.toSet());
        Map<Long, List<Author>> authorsMap = authorDao.findByBookIds(bookIds);
        Map<Long, List<Genre>> genresMap = genreDao.findByBookIds(bookIds);

        books.forEach(book -> {
            book.setAuthors(authorsMap.get(book.getId()));
            book.setGenres(genresMap.get(book.getId()));
        });
    }
}
