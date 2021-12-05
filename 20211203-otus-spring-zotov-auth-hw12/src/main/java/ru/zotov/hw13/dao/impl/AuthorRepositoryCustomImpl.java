package ru.zotov.hw13.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw13.dao.AuthorRepositoryCustom;
import ru.zotov.hw13.domain.Author;
import ru.zotov.hw13.domain.Book;
import ru.zotov.hw13.exception.ConstrainDeleteException;

import java.util.List;

/**
 * @author Created by ZotovES on 11.11.2021
 * Реализация кастомного репозитория авторов
 */
@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Удаление авторов с проверкой зависимых сущностей
     *
     * @param ids список ид авторов
     */
    @Override
    public void deleteWithConstraintsByIds(List<String> ids) throws ConstrainDeleteException {
        if (mongoTemplate.exists(new Query(Criteria.where("authors.$id").in(ids)), Book.class)) {
            throw new ConstrainDeleteException("Impossible remove author");
        }
        mongoTemplate.remove(new Query(Criteria.where("id").in(ids)), Author.class);
    }
}
