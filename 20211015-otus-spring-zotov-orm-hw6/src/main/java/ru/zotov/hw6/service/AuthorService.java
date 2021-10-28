package ru.zotov.hw6.service;

import ru.zotov.hw6.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 28.10.2021
 * Сервис авторов
 */
public interface AuthorService {
    /**
     * Создать автора
     *
     * @param author автор
     * @return автор
     */
    Author create(Author author);

    /**
     * Редактировать автора
     *
     * @param author Автор
     * @return Автор
     */
    Author update(Author author);

    /**
     * Получить автора по ид
     *
     * @param id ид
     * @return автор
     */
    Optional<Author> findById(Long id);

    /**
     * Удалить автора
     *
     * @param id ид
     */
    void deleteById(Long id);

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
}
