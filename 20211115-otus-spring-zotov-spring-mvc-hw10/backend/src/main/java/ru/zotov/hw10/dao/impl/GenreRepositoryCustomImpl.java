package ru.zotov.hw10.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw10.dao.GenreRepositoryCustom;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Genre;

import java.util.List;

/**
 * @author Created by ZotovES on 11.11.2021
 * Реализация кастомного репозитория жанров
 */
@RequiredArgsConstructor
public class GenreRepositoryCustomImpl implements GenreRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Удаление с проверкой зависимых сущностей
     *
     * @param genreIds список ид жанров
     */
    @Override
    public void deleteWithConstraintsByIds(List<String> genreIds) {
        if (mongoTemplate.exists(new Query(Criteria.where("genres.$id").in(genreIds)), Book.class)) {
            throw new IllegalArgumentException("Impossible remove genre");
        }
        mongoTemplate.remove(new Query(Criteria.where("id").in(genreIds)), Genre.class);
    }
}
