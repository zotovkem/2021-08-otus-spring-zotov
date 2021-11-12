package ru.zotov.hw8.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw8.dao.BookRepositoryCustom;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Comment;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

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
        Optional.ofNullable(mongoTemplate.findAndRemove(new Query(where("id").is(bookId)), Book.class)).ifPresent(book -> {
            List<String> commentsIds = book.getComments().stream()
                    .map(Comment::getId)
                    .collect(Collectors.toList());
            mongoTemplate.remove(new Query(where("id").in(commentsIds)), Comment.class);
        });
    }
}
