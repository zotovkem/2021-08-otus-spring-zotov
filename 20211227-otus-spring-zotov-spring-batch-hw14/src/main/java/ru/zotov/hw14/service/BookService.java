package ru.zotov.hw14.service;

import ru.zotov.hw14.domain.BookMongo;

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
    BookMongo save(BookMongo book);

    /**
     * Удалить книгу по ид
     *
     * @param ids список ид
     */
    void deleteByIds(List<String> ids);

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    List<BookMongo> findByAll();

    /**
     * Найти книгу по ид
     *
     * @param id ид
     * @return книга
     */
    Optional<BookMongo> findById(String id);
}
