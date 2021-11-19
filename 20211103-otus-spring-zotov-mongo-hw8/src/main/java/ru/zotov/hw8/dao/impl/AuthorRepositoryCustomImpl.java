package ru.zotov.hw8.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw8.dao.AuthorRepositoryCustom;
import ru.zotov.hw8.domain.Author;
import ru.zotov.hw8.domain.Book;

/**
 * @author Created by ZotovES on 11.11.2021
 * Реализация кастомного репозитория авторов
 */
@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Удаление с проверкой зависимых сущностей
     *
     * @param id ид автора
     */
    @Override
    public void deleteWithConstraintsById(String id) {
        if (mongoTemplate.exists(new Query(Criteria.where("authors.id").is(id)), Book.class)) {
            throw new IllegalArgumentException("Impossible remove author");
        }
        mongoTemplate.remove(new Query(Criteria.where("id").is(id)), Author.class);
    }
}
