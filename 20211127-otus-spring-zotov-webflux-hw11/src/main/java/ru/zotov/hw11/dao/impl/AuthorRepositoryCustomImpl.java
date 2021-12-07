package ru.zotov.hw11.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import ru.zotov.hw11.dao.AuthorRepositoryCustom;
import ru.zotov.hw11.domain.Author;
import ru.zotov.hw11.domain.Book;
import ru.zotov.hw11.exception.ConstrainDeleteException;

import java.util.List;

/**
 * @author Created by ZotovES on 11.11.2021
 * Реализация кастомного репозитория авторов
 */
@RequiredArgsConstructor
public class AuthorRepositoryCustomImpl implements AuthorRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    /**
     * Удаление авторов с проверкой зависимых сущностей
     *
     * @param ids список ид авторов
     */
    @Override
    public Mono<Void> deleteWithConstraintsByIds(List<String> ids) {
        return mongoTemplate.exists(new Query(Criteria.where("authors._id").in(ids)), Book.class)
                .filter(Boolean.TRUE::equals)
                .map(exist -> {
                    throw new ConstrainDeleteException("Impossible remove author");
                })
                .switchIfEmpty(mongoTemplate.remove(new Query(Criteria.where("id").in(ids)), Author.class))
                .then();
    }
}
