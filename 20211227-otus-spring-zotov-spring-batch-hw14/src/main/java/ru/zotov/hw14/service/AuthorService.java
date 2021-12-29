package ru.zotov.hw14.service;

import ru.zotov.hw14.domain.AuthorMongo;
import ru.zotov.hw14.exception.ConstrainDeleteException;

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
    AuthorMongo save(AuthorMongo author);

    /**
     * Получить автора по ид
     *
     * @param id ид
     * @return автор
     */
    Optional<AuthorMongo> findById(String id);

    /**
     * Удалить авторов по списку ид
     *
     * @param ids список ид
     */
    void deleteByListIds(List<String> ids) throws ConstrainDeleteException;

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    List<AuthorMongo> findByAll();

    /**
     * Поиск авторов по списку ид
     *
     * @param ids список ид
     * @return список авторов
     */
    Set<AuthorMongo> findByIdIn(List<String> ids);
}
