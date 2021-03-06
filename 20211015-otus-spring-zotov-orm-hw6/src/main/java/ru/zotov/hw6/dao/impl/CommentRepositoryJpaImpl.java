package ru.zotov.hw6.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw6.dao.CommentRepository;
import ru.zotov.hw6.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 22.10.2021
 * Реализация репозитория комментариев к книге
 */
@Service
@RequiredArgsConstructor
public class CommentRepositoryJpaImpl implements CommentRepository {
    @PersistenceContext
    private final EntityManager em;

    /**
     * Создать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    public Comment create(Comment comment) {
        if (comment.getId() == null) {
            em.persist(comment);
            return comment;
        }
        return update(comment);
    }

    /**
     * Редактировать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    public Comment update(Comment comment) {
        return em.merge(comment);
    }

    /**
     * Удалить комментарий
     *
     * @param comment комментарий
     */
    @Override
    public void delete(Comment comment) {
        em.remove(comment);
    }

    /**
     * Поиск комментария по ид
     *
     * @param id ид
     * @return комментарий
     */
    @Override
    public Optional<Comment> findById(Long id) {
        return Optional.ofNullable(em.find(Comment.class, id));
    }

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    @Override
    public List<Comment> findAll() {
        return em.createQuery("select c from Comment c", Comment.class).getResultList();
    }
}
