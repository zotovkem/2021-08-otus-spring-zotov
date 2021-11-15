package ru.zotov.hw10.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.zotov.hw10.domain.Comment;

/**
 * @author Created by ZotovES on 22.10.2021
 * Репозиторий комментариев
 */
public interface CommentRepository extends MongoRepository<Comment, String>, CommentRepositoryCustom {
}
