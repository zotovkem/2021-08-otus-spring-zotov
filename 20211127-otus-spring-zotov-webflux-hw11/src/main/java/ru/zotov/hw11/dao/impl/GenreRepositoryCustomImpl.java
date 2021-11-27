package ru.zotov.hw11.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
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
    private final MongoTemplate mongoTemplate;

    /**
     * Удаление с проверкой зависимых сущностей
     *
     * @param genreIds список ид жанров
     */
    @Override
    public void deleteWithConstraintsByIds(List<String> genreIds) throws ConstrainDeleteException {
        if (mongoTemplate.exists(new Query(Criteria.where("genres.$id").in(genreIds)), Book.class)) {
            throw new ConstrainDeleteException("Impossible remove genre");
        }
        mongoTemplate.remove(new Query(Criteria.where("id").in(genreIds)), Genre.class);
    }
}
