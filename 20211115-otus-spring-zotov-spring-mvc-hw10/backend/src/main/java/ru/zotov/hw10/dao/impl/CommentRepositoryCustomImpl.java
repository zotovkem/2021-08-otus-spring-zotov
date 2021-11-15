package ru.zotov.hw10.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.lang.NonNull;
import ru.zotov.hw10.dao.CommentRepositoryCustom;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Comment;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author Created by ZotovES on 12.11.2021
 * Реализация кастомного репозитория коментариев
 */
@RequiredArgsConstructor
public class CommentRepositoryCustomImpl implements CommentRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Каскадное обновление комментария
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    public Comment cascadeSave(@NonNull Comment comment) {
        List<Comment> comments = Optional.ofNullable(comment.getId())
                .map(id -> new Query(where("comments.id").is(id)))
                .map(query -> mongoTemplate.findOne(query, Book.class))
                .map(Book::getComments).stream().flatMap(Collection::stream)
                .filter(c -> !c.getId().equals(comment.getId()))
                .collect(Collectors.toList());
        comments.add(comment);

        if (mongoTemplate.updateFirst(new Query(where("comments.id").is(comment.getId())),
                new Update().set("comments", comments), Book.class).getModifiedCount() <= 0) {
            throw new IllegalArgumentException("Not found comment");
        }

        return mongoTemplate.save(comment);
    }

    /**
     * Каскадное удаление комментария
     *
     * @param commentId ид комментария
     */
    @Override
    public void cascadeDelete(String commentId) {
        Comment comment = mongoTemplate.findAndRemove(new Query(where("id").is(commentId)), Comment.class);

        mongoTemplate.updateFirst(new Query(where("comments.id").is(commentId)),
                new Update().pull("comments", comment), Book.class);

    }
}
