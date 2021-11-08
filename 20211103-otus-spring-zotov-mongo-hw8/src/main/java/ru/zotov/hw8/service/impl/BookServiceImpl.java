package ru.zotov.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw8.dao.BookRepository;
import ru.zotov.hw8.domain.Author;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Genre;
import ru.zotov.hw8.service.AuthorService;
import ru.zotov.hw8.service.BookService;
import ru.zotov.hw8.service.GenreService;

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
    private final AuthorService authorService;
    private final GenreService genreService;

    /**
     * Сохранить книгу
     *
     * @param book Книга
     * @return книга
     */
    @Override
    @Transactional
    public Book save(Book book) {
        List<String> authorIds = book.getAuthors().stream()
                .map(Author::getId)
                .collect(Collectors.toList());
        List<String> genreIds = book.getGenres().stream()
                .map(Genre::getId)
                .collect(Collectors.toList());
        book.setAuthors(authorService.findByIdIn(authorIds));
        book.setGenres(genreService.findByIdIn(genreIds));

        return bookDao.save(book);
    }

    /**
     * Удалить книгу по ид
     *
     * @param id ид
     */
    @Override
    @Transactional
    public void deleteById(String id) {
        bookDao.cascadeDeleteById(id);
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
    public Optional<Book> findById(String id) {
        return bookDao.findById(id);
    }

    /**
     * Найти книги по наименованию
     *
     * @param name наименование
     * @return список книг
     */
    @Override
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
    public List<Book> findByGenreName(String name) {
        return bookDao.findByGenreName(name);
    }

    /**
     * Поиск книг по фио автора
     *
     * @param fio фио автора
     * @return список книг
     */
    @Override
    public List<Book> findByAuthorFio(String fio) {
        return bookDao.findByAuthorFio(fio);
    }
}
