package ru.zotov.hw5.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.zotov.hw5.dao.BookDao;
import ru.zotov.hw5.dao.mapper.BookMapper;
import ru.zotov.hw5.domain.Book;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория книг
 */
@SuppressWarnings("SqlResolve")
@Repository
@RequiredArgsConstructor
public class BookDaoImpl implements BookDao {
    private final NamedParameterJdbcOperations jdbc;

    /**
     * Добавить книгу
     *
     * @param book книга
     */
    @Override
    public Book create(Book book) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource fileParameters =
                new MapSqlParameterSource(Map.of("name", book.getName(), "releaseYear", book.getReleaseYear()));
        jdbc.update("insert into book(name,release_year) values(:name,:releaseYear)", fileParameters, keyHolder);

        Optional.ofNullable(keyHolder.getKey())
                .map(Number::longValue)
                .ifPresent(book::setId);

        return book;
    }

    /**
     * Редактировать книгу
     *
     * @param book книга
     * @return книга
     */
    @Override
    public Book update(Book book) {
        jdbc.update("update book set name= :name, release_year= :releaseYear where id = :id",
                Map.of("name", book.getName(), "releaseYear", book.getReleaseYear(), "id", book.getId()));
        return getById(book.getId()).orElseThrow();
    }

    /**
     * Удалить книгу
     *
     * @param id ид
     */
    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from book where id = :id", Map.of("id", id));
    }

    /**
     * Получить книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @Override
    public Optional<Book> getById(Long id) {
        return Optional.ofNullable(jdbc.queryForObject("select id,name,release_year from book where id = :id",
                Map.of("id", id), new BookMapper()));
    }

    /**
     * Получить все книги
     *
     * @return список книг
     */
    @Override
    public List<Book> findAll() {
        return jdbc.query("select id,name,release_year from book", new BookMapper());
    }

    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    @Override
    public List<Book> findByName(String name) {
        return jdbc.query("select id,name,release_year from book where lower(name) like concat(lower(:name),'%')",
                Map.of("name", name), new BookMapper());
    }

    /**
     * Найти книги по ФИО автора
     *
     * @param authorFio фио автора книги
     * @return Список книг
     */
    @Override
    public List<Book> findByAuthorFio(String authorFio) {
        return jdbc.query("select id,name,release_year from book b " +
                        "where exists (select * from mtm_book_author mba left join author a on (mba.author_id = a.id) " +
                        "where b.id = mba.book_id and lower(a.fio) like concat(lower(:fio),'%'))",
                Map.of("fio", authorFio), new BookMapper());
    }

    /**
     * Найти книги по жанру
     *
     * @param genreName жанр
     * @return список книг
     */
    @Override
    public List<Book> findByGenreName(String genreName) {
        return jdbc.query("select id,name,release_year from book b " +
                        "where exists (select * from mtm_book_genre mbg left join genre g on (mbg.genre_id = g.id) " +
                        "where b.id = mbg.book_id and lower(g.name) like concat(lower(:genre),'%'))",
                Map.of("genre", genreName), new BookMapper());
    }

    /**
     * Добавить книге жанр
     *
     * @param bookId  ид книги
     * @param genreId ид автора
     */
    @Override
    public void addGenre(Long bookId, Long genreId) {
        jdbc.update("insert into mtm_book_genre(book_id,genre_id) values(:bookId,:genreId)",
                Map.of("bookId", bookId, "genreId", genreId));
    }

    /**
     * Добавить книги автора
     *
     * @param bookId   ид книги
     * @param authorId ид жанра
     */
    @Override
    public void addAuthor(Long bookId, Long authorId) {
        jdbc.update("insert into mtm_book_author(book_id,author_id) values(:bookId,:authorId)",
                Map.of("bookId", bookId, "authorId", authorId));
    }

    /**
     * Удалить у книги все ссылки на жанры
     *
     * @param bookId ид книги
     */
    @Override
    public void deleteAllRefGenre(Long bookId) {
        jdbc.update("delete from mtm_book_genre where book_id = :bookId", Map.of("bookId", bookId));

    }

    /**
     * Удалить у кинги все ссылки на авторов
     *
     * @param bookId ид книги
     */
    @Override
    public void deleteAllRefAuthor(Long bookId) {
        jdbc.update("delete from mtm_book_author where book_id = :bookId", Map.of("bookId", bookId));
    }
}
