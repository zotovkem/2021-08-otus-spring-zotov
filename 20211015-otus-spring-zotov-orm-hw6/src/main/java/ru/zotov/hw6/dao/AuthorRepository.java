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
     * @param author автор
     */
    void delete(Author author);

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    List<Author> findByAll();

    /**
     * Поиск автора по фио
     *
     * @param fio фио автора
     * @return автор
     */
    List<Author> findByFio(String fio);
}
