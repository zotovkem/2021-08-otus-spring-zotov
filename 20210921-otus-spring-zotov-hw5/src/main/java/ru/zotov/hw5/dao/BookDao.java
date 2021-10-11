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
