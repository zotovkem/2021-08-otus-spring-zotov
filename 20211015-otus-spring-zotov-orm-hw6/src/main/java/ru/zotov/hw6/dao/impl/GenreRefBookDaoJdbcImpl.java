package ru.zotov.hw6.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.zotov.hw6.dao.GenreRefBookDao;
import ru.zotov.hw6.dao.mapper.GenreToMapEntryMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * @author Created by ZotovES on 11.10.2021
 * Рефлизация репозитория ссылок книг на жанры
 */
@SuppressWarnings("SqlResolve")
@Repository
@RequiredArgsConstructor
public class GenreRefBookDaoJdbcImpl implements GenreRefBookDao {
    private final NamedParameterJdbcOperations jdbc;
    private final GenreToMapEntryMapper genreToMapEntryMapper;

    /**
     * Найти все жанры по ид книги
     *
     * @param bookIds список ид книг и жанров
     * @return список жанров
     */
    @Override
    public Map<Long, List<Long>> findByBookIds(Collection<Long> bookIds) {
        return jdbc.queryForStream("select book_id,genre_id from mtm_book_genre m  where book_id in (:bookIds)",
                        Map.of("bookIds", bookIds), genreToMapEntryMapper)
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())));
    }

    /**
     * Добавить книге жанр
     *
     * @param bookId  ид книги
     * @param genreId ид автора
     */
    @Override
    public void addRefGenre(Long bookId, Long genreId) {
        jdbc.update("insert into mtm_book_genre(book_id,genre_id) values(:bookId,:genreId)",
                Map.of("bookId", bookId, "genreId", genreId));
    }

    /**
     * Удалить у книги все ссылки на жанры
     *
     * @param bookId ид книги
     */
    @Override
    public void deleteAllRefGenre(Long bookId) {
        jdbc.update("delete from mtm_book_genre where book_id = :bookId", Map.of("bookId", bookId));
    }
}
