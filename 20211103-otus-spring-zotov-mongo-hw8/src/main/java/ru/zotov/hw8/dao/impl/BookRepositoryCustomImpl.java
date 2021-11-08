package ru.zotov.hw8.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw8.dao.BookRepositoryCustom;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Comment;

/**
 * @author Created by ZotovES on 08.11.2021
 * Реализация кастомного репозитория книг
 */
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Каскадное удаление зависимых сущностей
     *
     * @param bookId ид книги
     */
    @Override
    public void cascadeDeleteById(String bookId) {
        mongoTemplate.remove(new Query(Criteria.where("book.id").is(bookId)), Comment.class);
        mongoTemplate.remove(new Query(Criteria.where("id").is(bookId)), Book.class);
    }
}
