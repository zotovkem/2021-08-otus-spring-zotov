package ru.zotov.hw7.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw7.domain.Comment;

import java.util.List;

/**
 * @author Created by ZotovES on 22.10.2021
 * Репозиторий комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * Получить список комментариев по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    List<Comment> findByBookId(Long bookId);
}
