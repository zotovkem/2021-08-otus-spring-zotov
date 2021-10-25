package ru.zotov.hw6.dao;

import ru.zotov.hw6.domain.Author;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Авторов книг
 */
public interface AuthorRepository {
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
}
