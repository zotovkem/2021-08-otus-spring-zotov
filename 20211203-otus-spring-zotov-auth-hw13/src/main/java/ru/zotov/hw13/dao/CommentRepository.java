package ru.zotov.hw13.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import ru.zotov.hw13.domain.Comment;

import java.util.List;

/**
 * @author Created by ZotovES on 22.10.2021
 * Репозиторий комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Override
    @PostFilter("hasPermission(filterObject, 'READ') || !hasRole('CHILD')")
    List<Comment> findAll();

    @PreAuthorize("hasPermission(#entity,'DELETE') || hasRole('ADMIN')")
    @Override
    void delete(Comment entity);
}
