package ru.zotov.hw5.service;

import ru.zotov.hw5.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Сервис книг
 */
public interface BookService {
    /**
     * Создать книгу
     *
     * @param book книга
     */
    void create(Book book);

    /**
     * Редактировать книгу
     *
     * @param book Книга
     * @return книга
     */
    Book update(Book book);

    /**
     * Удалить книгу по ид
     *
     * @param id ид
     */
    void deleteById(Long id);

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    List<Book> findByAll();

    /**
     * Найти книгу по ид
     *
     * @param id ид
     * @return книга
     */
    Optional<Book> findById(Long id);
}
