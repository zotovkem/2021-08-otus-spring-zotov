package ru.zotov.hw14.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw14.dao.AuthorRepositoryMongoCustom;
import ru.zotov.hw14.domain.AuthorMongo;
import ru.zotov.hw14.domain.BookMongo;
import ru.zotov.hw14.exception.ConstrainDeleteException;

import java.util.List;

/**
 * @author Created by ZotovES on 11.11.2021
 * Реализация кастомного репозитория авторов
 */
@RequiredArgsConstructor
public class AuthorRepositoryMongoCustomImpl implements AuthorRepositoryMongoCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Удаление авторов с проверкой зависимых сущностей
     *
     * @param ids список ид авторов
     */
    @Override
    public void deleteWithConstraintsByIds(List<String> ids) throws ConstrainDeleteException {
        if (mongoTemplate.exists(new Query(Criteria.where("authors.$id").in(ids)), BookMongo.class)) {
            throw new ConstrainDeleteException("Impossible remove author");
        }
        mongoTemplate.remove(new Query(Criteria.where("id").in(ids)), AuthorMongo.class);
    }
}
