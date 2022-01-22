package ru.zotov.hw17.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import ru.zotov.hw17.domain.Comment;

import java.util.List;

/**
 * @author Created by ZotovES on 22.10.2021
 * Репозиторий комментариев
 */
@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Override
    @PostFilter("hasPermission(filterObject, 'READ') or !hasRole('CHILD')")
    List<Comment> findAll();

    @Override
    @PreAuthorize("hasPermission(#entity,'DELETE') or hasRole('ADMIN')")
    void delete(Comment entity);
}
