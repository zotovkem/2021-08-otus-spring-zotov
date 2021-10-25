package ru.zotov.hw7.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.zotov.hw7.dao.AuthorRepository;
import ru.zotov.hw7.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория авторов
 */
@Repository
@RequiredArgsConstructor
public class AuthorRepositoryJpaImpl implements AuthorRepository {
    @PersistenceContext
    private final EntityManager em;

    /**
     * Создать автора
     *
     * @param author автор
     * @return автор
     */
    @Override
    public Author create(Author author) {
        if (author.getId() == null) {
            em.persist(author);
            return author;
        }
        return update(author);
    }

    /**
     * Редактировать автора
     *
     * @param author Автор
     * @return Автор
     */
    @Override
    public Author update(Author author) {
        return em.merge(author);
    }

    /**
     * Получить автора по ид
     *
     * @param id ид
     * @return автор
     */
    @Override
    public Optional<Author> findById(Long id) {
        return Optional.ofNullable(em.find(Author.class, id));
    }

    /**
     * Удалить автора
     *
     * @param id ид
     */
    @Override
    public void deleteById(Long id) {
        em.createQuery("delete from Author where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    @Override
    public List<Author> findByAll() {
        return em.createQuery("select a from Author a", Author.class).getResultList();
    }
}
