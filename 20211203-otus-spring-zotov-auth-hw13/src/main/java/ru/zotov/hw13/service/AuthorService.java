package ru.zotov.hw13.service;

import ru.zotov.hw13.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 28.10.2021
 * Сервис авторов
 */
public interface AuthorService {
    /**
     * Сохранить автора
     *
     * @param author Автор
     * @return Автор
     */
    Author save(Author author);

    /**
     * Получить автора по ид
     *
     * @param id ид
     * @return автор
     */
    Optional<Author> findById(Long id);

    /**
     * Удалить авторов по списку ид
     *
     * @param ids список ид
     */
    void deleteByListIds(List<Long> ids);

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    List<Author> findByAll();
}
