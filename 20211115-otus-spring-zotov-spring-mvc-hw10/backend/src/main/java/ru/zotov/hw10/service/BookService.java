package ru.zotov.hw10.service;

import ru.zotov.hw10.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Сервис книг
 */
public interface BookService {
    /**
     * Сохранить книгу
     *
     * @param book Книга
     * @return книга
     */
    Book save(Book book);

    /**
     * Удалить книгу по ид
     *
     * @param id ид
     */
    void deleteById(String id);

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    List<Book> findByAll();

    /**
     * Найти книги по наименованию
     *
     * @param name наименование
     * @return список книг
     */
    List<Book> findByName(String name);

    /**
     * Найти книги по жанру
     *
     * @param name наименование жанра
     * @return список книг
     */
    List<Book> findByGenreName(String name);

    /**
     * Поиск книг по фио автора
     *
     * @param fio фио автора
     * @return список книг
     */
    List<Book> findByAuthorFio(String fio);

    /**
     * Найти книгу по ид
     *
     * @param id ид
     * @return книга
     */
    Optional<Book> findById(String id);
}
