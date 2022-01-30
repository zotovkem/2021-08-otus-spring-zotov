package ru.zotov.hw18.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw18.domain.Comment;

import java.util.List;

/**
 * @author Created by ZotovES on 22.10.2021
 * Репозиторий комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
