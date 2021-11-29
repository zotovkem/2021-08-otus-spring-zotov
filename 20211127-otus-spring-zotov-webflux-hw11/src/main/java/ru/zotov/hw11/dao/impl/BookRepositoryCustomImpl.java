package ru.zotov.hw11.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import reactor.core.publisher.Mono;
import ru.zotov.hw11.dao.BookRepositoryCustom;
import ru.zotov.hw11.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 08.11.2021
 * Реализация кастомного репозитория книг
 */
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final ReactiveMongoTemplate mongoTemplate;

    /**
     * Удаление книг по списку ид
     *
     * @param ids список ид книг
     */
    @Override
    public Mono<Void> deleteByIds(List<String> ids) {
        return mongoTemplate.remove(new Query(Criteria.where("id").in(ids)), Book.class).then();
    }
}
