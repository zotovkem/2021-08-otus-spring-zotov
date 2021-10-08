package ru.zotov.hw5.dao;

import ru.zotov.hw5.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
public interface BookDao {
    /**
     * Добавить книгу
     *
     * @param book книга
     */
    Book create(Book book);

    /**
     * Редактировать книгу
     *
     * @param book книга
     * @return книга
     */
    Book update(Book book);

    /**
     * Удалить книгу
     *
     * @param id ид
     */
    void deleteById(Long id);

    /**
     * Получить книгу по ид
     *
     * @param id ид
     * @return книга
     */
    Optional<Book> getById(Long id);

    /**
     * Получить все книги
     *
     * @return список книг
     */
    List<Book> findAll();

    /**
     * Добавить книге жанр
     *
     * @param bookId  ид книги
     * @param genreId ид автора
     */
    void addGenre(Long bookId, Long genreId);

    /**
     * Добавить книги автора
     *
     * @param bookId   ид книги
     * @param authorId ид жанра
     */
    void addAuthor(Long bookId, Long authorId);

    /**
     * Удалить у книги все ссылки на жанры
     *
     * @param bookId ид книги
     */
    void deleteAllRefGenre(Long bookId);

    /**
     * Удалить у кинги все ссылки на авторов
     *
     * @param bookId ид книги
     */
    void deleteAllRefAuthor(Long bookId);

    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    List<Book> findByName(String name);

    /**
     * Найти книги по ФИО автора
     *
     * @param authorFio фио автора книги
     * @return Список книг
     */
    List<Book> findByAuthorFio(String authorFio);

    /**
     * Найти книги по жанру
     *
     * @param genreName жанр
     * @return список книг
     */
    List<Book> findByGenreName(String genreName);
}
