package ru.zotov.hw10.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw10.dao.BookRepository;
import ru.zotov.hw10.domain.Author;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Comment;
import ru.zotov.hw10.domain.Genre;
import ru.zotov.hw10.service.AuthorService;
import ru.zotov.hw10.service.BookService;
import ru.zotov.hw10.service.GenreService;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
        List<Comment> comments = Optional.ofNullable(book.getComments()).orElse(Collections.emptyList());
        comments.forEach(comment -> {
            if (comment.getId() == null) {
                comment.setId(UUID.randomUUID().toString());
            }
            if (comment.getCreateDate() == null) {
                comment.setCreateDate(ZonedDateTime.now());
            }
        });
        book.setComments(comments);
        book.setAuthors(authorService.findByIdIn(authorIds));
        book.setGenres(genreService.findByIdIn(genreIds));

        return bookDao.save(book);
    }

    /**
     * Удалить книгу по ид
     *
     * @param ids список ид
     */
    @Override
    @Transactional
    public void deleteByIds(List<String> ids) {
        bookDao.deleteByIds(ids);
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
}
