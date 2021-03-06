package ru.zotov.hw16.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw16.dao.BookRepositoryCustom;
import ru.zotov.hw16.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 08.11.2021
 * Реализация кастомного репозитория книг
 */
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Удаление книг по списку ид
     *
     * @param ids список ид книг
     */
    @Override
    public void deleteByIds(List<String> ids) {
        mongoTemplate.remove(new Query(Criteria.where("id").in(ids)), Book.class);
    }
}
