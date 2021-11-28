package ru.zotov.hw11.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import ru.zotov.hw11.dao.GenreRepositoryCustom;
import ru.zotov.hw11.domain.Book;
import ru.zotov.hw11.domain.Genre;
import ru.zotov.hw11.exception.ConstrainDeleteException;

import java.util.List;

/**
 * @author Created by ZotovES on 11.11.2021
 * Реализация кастомного репозитория жанров
 */
@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    /**
     * Удаление с проверкой зависимых сущностей
     *
     * @param genreIds список ид жанров
     */
    @Override
    public Mono<Void> deleteWithConstraintsByIds(List<String> genreIds) throws ConstrainDeleteException {
        return mongoTemplate.exists(new Query(Criteria.where("genres.$id").in(genreIds)), Book.class)
                .filter(Boolean.TRUE::equals)
                .map(exist -> {
                    throw new ConstrainDeleteException("Impossible remove genre");
                })
                .then()
                .switchIfEmpty(mongoTemplate.remove(new Query(Criteria.where("id").in(genreIds)), Genre.class).then());
    }
}
