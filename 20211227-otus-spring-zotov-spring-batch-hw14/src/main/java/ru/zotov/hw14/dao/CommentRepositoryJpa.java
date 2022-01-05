package ru.zotov.hw14.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw14.domain.jpa.CommentJpa;

/**
 * @author Created by ZotovES on 22.10.2021
 * Репозиторий комментариев
 */
@Repository
public interface CommentRepositoryJpa extends JpaRepository<CommentJpa, Long> {
}
