package ru.zotov.hw13.service;

import ru.zotov.hw13.domain.Book;

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
     * @param ids список ид
     */
    void deleteByIds(List<Long> ids);

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
