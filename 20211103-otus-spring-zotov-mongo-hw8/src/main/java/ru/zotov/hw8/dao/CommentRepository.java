package ru.zotov.hw8.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import ru.zotov.hw8.domain.Comment;

import java.util.List;

/**
 * @author Created by ZotovES on 22.10.2021
 * Репозиторий комментариев
 */
public interface CommentRepository extends MongoRepository<Comment, String> {
    /**
     * Поиск по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    List<Comment> findByBookId(@Param("bookId") String bookId);
}
