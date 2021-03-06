package ru.zotov.hw8.service;

import ru.zotov.hw8.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    Optional<Author> findById(String id);

    /**
     * Удалить автора
     *
     * @param id ид
     */
    void deleteById(String id);

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    List<Author> findByAll();

    /**
     * Поиск авторов по фио
     *
     * @param fio фио автора
     * @return список авторов
     */
    List<Author> findByFio(String fio);

    /**
     * Поиск авторов по списку ид
     *
     * @param ids список ид
     * @return список авторов
     */
    Set<Author> findByIdIn(List<String> ids);
}
