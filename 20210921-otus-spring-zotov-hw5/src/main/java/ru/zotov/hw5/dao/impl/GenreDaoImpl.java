package ru.zotov.hw5.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.zotov.hw5.dao.GenreDao;
import ru.zotov.hw5.dao.mapper.GenreMapper;
import ru.zotov.hw5.dao.mapper.GenreToMapEntryMapper;
import ru.zotov.hw5.domain.Genre;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория жанров
 */
@SuppressWarnings("SqlResolve")
@Repository
@RequiredArgsConstructor
public class GenreDaoImpl implements GenreDao {
    private final NamedParameterJdbcOperations jdbc;

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
    public Optional<Genre> getById(long id) {
        return Optional.ofNullable(
                jdbc.queryForObject("select id,name from genre where id = :id", Map.of("id", id), new GenreMapper()));
    }

    /**
     * Удалить по ид
     *
     * @param id ид жанра
     */
    @Override
    public void deleteById(long id) {
        jdbc.update("delete from genre where id = : id", Map.of("id", id));
    }

    /**
     * Найти по списку идентификаторов
     *
     * @param ids список ид
     * @return список жанров
     */
    @Override
    public List<Genre> findByIdsIn(Collection<Long> ids) {
        return jdbc.query("select id,name from genre where id in (:ids)", Map.of("ids", ids), new GenreMapper());
    }

    /**
     * Найти все жанры по ид книги
     *
     * @param bookIds список ид книг
     * @return список жанров
     */
    @Override
    public Map<Long, List<Genre>> findByBookIds(Collection<Long> bookIds) {
        Map<Long, List<Long>> genreIds =
                jdbc.queryForStream("select book_id,genre_id from mtm_book_genre m  where book_id in (:bookIds)",
                                Map.of("bookIds", bookIds), new GenreToMapEntryMapper())
                        .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())));

        List<Genre> genres = findByIdsIn(genreIds.values().stream().flatMap(Collection::stream).collect(toList()));

        return genreIds.entrySet().stream()
                .collect(toMap(Map.Entry::getKey,
                        e -> genres.stream().filter(genre -> e.getValue().contains(genre.getId())).collect(toList())));
    }
}
