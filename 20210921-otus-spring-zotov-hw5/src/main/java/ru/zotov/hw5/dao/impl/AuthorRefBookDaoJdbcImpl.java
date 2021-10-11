package ru.zotov.hw5.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.zotov.hw5.dao.AuthorRefBookDao;
import ru.zotov.hw5.dao.mapper.AuthorToMapEntryMapper;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;

/**
 * @author Created by ZotovES on 11.10.2021
 * Реализация репозитория ссылок книг на авторов
 */
@SuppressWarnings("SqlResolve")
@Repository
@RequiredArgsConstructor
public class AuthorRefBookDaoJdbcImpl implements AuthorRefBookDao {
    private final NamedParameterJdbcOperations jdbc;

    /**
     * Найти всех авторов по списку ид книг
     *
     * @param bookIds список ид книг и авторов
     * @return список авторов
     */
    @Override
    public Map<Long, List<Long>> findByBookIds(Collection<Long> bookIds) {
        return jdbc.queryForStream("select book_id,author_id from mtm_book_author m  where book_id in (:bookIds)",
                        Map.of("bookIds", bookIds), new AuthorToMapEntryMapper())
                .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())));

    }

    /**
     * Добавить книги автора
     *
     * @param bookId   ид книги
     * @param authorId ид жанра
     */
    @Override
    public void addRefAuthor(Long bookId, Long authorId) {
        jdbc.update("insert into mtm_book_author(book_id,author_id) values(:bookId,:authorId)",
                Map.of("bookId", bookId, "authorId", authorId));
    }

    /**
     * Удалить у кинги все ссылки на авторов
     *
     * @param bookId ид книги
     */
    @Override
    public void deleteAllRefAuthor(Long bookId) {
        jdbc.update("delete from mtm_book_author where book_id = :bookId", Map.of("bookId", bookId));
    }
}
