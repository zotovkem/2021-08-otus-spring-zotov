package ru.zotov.hw18.service;

import org.springframework.lang.Nullable;
import ru.zotov.hw18.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Сервис книг
 */
public interface BookService {
    /**
     * Редактировать книгу
     *
     * @param book Книга
     * @return книга
     */
    Book update(Book book);

    /**
     * Создать книгу
     *
     * @param book Книга
     * @return книга
     */
    Book create(Book book);

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
    @Nullable
    Book findById(Long id);
}
