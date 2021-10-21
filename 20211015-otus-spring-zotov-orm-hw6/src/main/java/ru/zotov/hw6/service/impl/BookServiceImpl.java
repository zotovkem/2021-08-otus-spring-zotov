package ru.zotov.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw6.dao.*;
import ru.zotov.hw6.domain.Author;
import ru.zotov.hw6.domain.Book;
import ru.zotov.hw6.domain.Genre;
import ru.zotov.hw6.service.BookService;

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
    private final GenreRefBookDao genreRefBookDao;
    private final AuthorRefBookDao authorRefBookDao;

    /**
     * Создать книгу
     *
     * @param book книга
     * @return книга
     */
    @Override
    public Book create(Book book) {
        Book savedBook = bookDao.create(book);
        book.getAuthors().forEach(author -> authorRefBookDao.addRefAuthor(savedBook.getId(), author.getId()));
        book.getGenres().forEach(genre -> genreRefBookDao.addRefGenre(savedBook.getId(), genre.getId()));
        return book;
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
        authorRefBookDao.deleteAllRefAuthor(book.getId());
        genreRefBookDao.deleteAllRefGenre(book.getId());
        book.getAuthors().forEach(author -> authorRefBookDao.addRefAuthor(book.getId(), author.getId()));
        book.getGenres().forEach(genre -> genreRefBookDao.addRefGenre(book.getId(), genre.getId()));

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
        return b -> setBookFieldsCollection(List.of(b));
    }

    /**
     * Проставляет в поля коллекция книги значения
     *
     * @param books список книг
     */
    private void setBookFieldsCollection(List<Book> books) {
        Set<Long> bookIds = books.stream().map(Book::getId).collect(Collectors.toSet());
        Map<Long, List<Long>> authorsMap = authorRefBookDao.findByBookIds(bookIds);
        Map<Long, List<Long>> genresMap = genreRefBookDao.findByBookIds(bookIds);
        List<Author> authors = authorDao.findByIdsIn(authorsMap.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toSet()));
        List<Genre> genres = genreDao.findByIdsIn(genresMap.values().stream()
                .flatMap(Collection::stream).collect(Collectors.toSet()));

        books.forEach(book -> {
            List<Author> filteredAuthors = authors.stream()
                    .filter(a -> authorsMap.getOrDefault(book.getId(), Collections.emptyList()).contains(a.getId()))
                    .collect(Collectors.toList());
            List<Genre> filteredGenres = genres.stream()
                    .filter(g -> genresMap.getOrDefault(book.getId(), Collections.emptyList()).contains(g.getId()))
                    .collect(Collectors.toList());

            book.setAuthors(filteredAuthors);
            book.setGenres(filteredGenres);
        });
    }
}
