package ru.zotov.hw14.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw14.dao.BookRepositoryMongoCustom;
import ru.zotov.hw14.domain.BookMongo;

import java.util.List;

/**
 * @author Created by ZotovES on 08.11.2021
 * Реализация кастомного репозитория книг
 */
@RequiredArgsConstructor
public class BookRepositoryMongoCustomImpl implements BookRepositoryMongoCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Удаление книг по списку ид
     *
     * @param ids список ид книг
     */
    @Override
    public void deleteByIds(List<String> ids) {
        mongoTemplate.remove(new Query(Criteria.where("id").in(ids)), BookMongo.class);
    }
}
