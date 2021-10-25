package ru.zotov.hw7.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.zotov.hw7.dao.CommentRepository;
import ru.zotov.hw7.domain.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 22.10.2021
 * Реализация репозитория комментариев к книге
 */
@Repository
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
     * @param id ид
     */
    @Override
    public void deleteById(Long id) {
        em.createQuery("delete from Comment where id = :id")
                .setParameter("id", id)
                .executeUpdate();
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

    /**
     * Получить список комментариев по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    @Override
    public List<Comment> findByBookId(Long bookId) {
        return em.createQuery("select c from Comment c where c.book.id = :bookId", Comment.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
