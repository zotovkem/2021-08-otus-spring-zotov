package ru.zotov.hw10.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw10.dao.GenreRepositoryCustom;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Genre;

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
     * @param genreId ид жанра
     */
    @Override
    public void deleteWithConstraintsById(String genreId) {
        if (mongoTemplate.exists(new Query(Criteria.where("genres.id").is(genreId)), Book.class)) {
            throw new IllegalArgumentException("Impossible remove genre");
        }
        mongoTemplate.remove(new Query(Criteria.where("id").is(genreId)), Genre.class);
    }
}
