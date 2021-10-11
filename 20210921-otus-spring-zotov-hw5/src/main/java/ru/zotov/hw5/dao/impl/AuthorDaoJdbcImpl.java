package ru.zotov.hw5.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.zotov.hw5.dao.AuthorDao;
import ru.zotov.hw5.dao.mapper.AuthorMapper;
import ru.zotov.hw5.domain.Author;

import java.util.*;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория авторов
 */
@SuppressWarnings("SqlResolve")
@Repository
@RequiredArgsConstructor
public class AuthorDaoJdbcImpl implements AuthorDao {
    private final NamedParameterJdbcOperations jdbc;

    /**
     * Создать автора
     *
     * @param author автор
     */
    @Override
    public void create(Author author) {
        jdbc.update("insert into author(fio) values(:fio)", Map.of("fio", author.getFio()));
    }

    /**
     * Редактировать автора
     *
     * @param author Автор
     * @return Автор
     */
    @Override
    public Author update(Author author) {
        jdbc.update("update author set fio= :fio where id = :id", Map.of("id", author.getId(), "fio", author.getFio()));
        return author;
    }

    /**
     * Получить автора по ид
     *
     * @param id ид
     * @return автор
     */
    @Override
    public Optional<Author> getById(Long id) {
        return jdbc.queryForStream("select id,fio from author where id = :id", Map.of("id", id), new AuthorMapper()).findFirst();
    }

    /**
     * Удалить автора
     *
     * @param id ид
     */
    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from author where id = :id", Map.of("id", id));
    }

    /**
     * Поиск авторов по списку ид
     *
     * @param ids список ид
     * @return список авторов
     */
    @Override
    public List<Author> findByIdsIn(Collection<Long> ids) {
        return jdbc.query("select id,fio from author where id in (:ids)", Map.of("ids", ids), new AuthorMapper());
    }

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    @Override
    public List<Author> findByAll() {
        return jdbc.query("select id,fio from author", Collections.emptyMap(), new AuthorMapper());
    }
}
