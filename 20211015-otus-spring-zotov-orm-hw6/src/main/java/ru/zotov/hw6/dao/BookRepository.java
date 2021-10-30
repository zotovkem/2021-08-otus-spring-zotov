package ru.zotov.hw6.dao;

import ru.zotov.hw6.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
public interface BookRepository {
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
     * @param book книга
     */
    void delete(Book book);

    /**
     * Получить книгу по ид
     *
     * @param id ид
     * @return книга
     */
    Optional<Book> findById(Long id);

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
}
