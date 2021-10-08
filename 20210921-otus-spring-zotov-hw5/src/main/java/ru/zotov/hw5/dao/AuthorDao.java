package ru.zotov.hw5.dao;

import ru.zotov.hw5.domain.Author;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Авторов книг
 */
public interface AuthorDao {
    /**
     * Создать автора
     *
     * @param author автор
     */
    void create(Author author);

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
    Optional<Author> getById(Long id);

    /**
     * Удалить автора
     *
     * @param id ид
     */
    void deleteById(Long id);

    /**
     * Поиск авторов по списку ид
     *
     * @param ids список ид
     * @return список авторов
     */
    List<Author> findByIdsIn(Collection<Long> ids);

    /**
     * Найти всех авторов по списку ид книг
     *
     * @param bookIds список ид книг
     * @return список авторов
     */
    Map<Long, List<Author>> findByBookIds(Collection<Long> bookIds);

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    List<Author> findByAll();
}
