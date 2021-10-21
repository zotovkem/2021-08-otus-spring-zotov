package ru.zotov.hw6.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.zotov.hw6.dao.GenreDao;
import ru.zotov.hw6.dao.mapper.GenreMapper;
import ru.zotov.hw6.domain.Genre;

import java.util.*;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория жанров
 */
@SuppressWarnings("SqlResolve")
@Repository
@RequiredArgsConstructor
public class GenreDaoJdbcImpl implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreMapper genreMapper;

    /**
     * Создать
     *
     * @param genre жанр
     */
    @Override
    public void create(Genre genre) {
        jdbc.update("insert into genre(name) values(:name)", Map.of("name", genre.getName()));
    }

    /**
     * Редактировать
     *
     * @param genre жанр
     * @return жанр
     */
    @Override
    public Genre update(Genre genre) {
        jdbc.update("update genre set name= :name where id = :id", Map.of("id", genre.getId(), "name", genre.getName()));
        return genre;
    }

    /**
     * Получить жанр по ид
     *
     * @param id ид
     * @return жанр
     */
    @Override
    public Optional<Genre> getById(Long id) {
        return jdbc.queryForStream("select id,name from genre where id = :id", Map.of("id", id), genreMapper).findFirst();
    }

    /**
     * Удалить по ид
     *
     * @param id ид жанра
     */
    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from genre where id = :id", Map.of("id", id));
    }

    /**
     * Найти по списку идентификаторов
     *
     * @param ids список ид
     * @return список жанров
     */
    @Override
    public List<Genre> findByIdsIn(Collection<Long> ids) {
        return jdbc.query("select id,name from genre where id in (:ids)", Map.of("ids", ids), genreMapper);
    }

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    @Override
    public List<Genre> getAll() {
        return jdbc.query("select id,name from genre", Collections.emptyMap(), genreMapper);
    }
}
