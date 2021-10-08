package ru.zotov.hw5.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import ru.zotov.hw5.dao.AuthorDao;
import ru.zotov.hw5.dao.mapper.AuthorMapper;
import ru.zotov.hw5.dao.mapper.AuthorToMapEntryMapper;
import ru.zotov.hw5.domain.Author;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.stream.Collectors.*;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория авторов
 */
@Repository
@RequiredArgsConstructor
public class AuthorDaoImpl implements AuthorDao {
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
        return Optional.ofNullable(
                jdbc.queryForObject("select id,fio from author where id = :id", Map.of("id", id), new AuthorMapper()));
    }

    /**
     * Удалить автора
     *
     * @param id ид
     */
    @Override
    public void deleteById(Long id) {
        jdbc.update("delete from author where id = : id", Map.of("id", id));
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
     * Найти всех авторов по списку ид книг
     *
     * @param bookIds список ид книг
     * @return список авторов
     */
    @Override
    public Map<Long, List<Author>> findByBookIds(Collection<Long> bookIds) {
        Map<Long, List<Long>> authorIds =
                jdbc.queryForStream("select book_id,author_id from mtm_book_author m  where book_id in (:bookIds)",
                                Map.of("bookIds", bookIds), new AuthorToMapEntryMapper())
                        .collect(groupingBy(Map.Entry::getKey, mapping(Map.Entry::getValue, toList())));

        List<Author> authors = findByIdsIn(authorIds.values().stream().flatMap(Collection::stream).collect(toList()));

        return authorIds.entrySet().stream()
                .collect(toMap(Map.Entry::getKey,
                        e -> authors.stream().filter(author -> e.getValue().contains(author.getId())).collect(toList())));
    }
}
