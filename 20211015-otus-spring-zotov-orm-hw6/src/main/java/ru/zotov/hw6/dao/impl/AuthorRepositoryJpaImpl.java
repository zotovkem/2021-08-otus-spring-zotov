package ru.zotov.hw6.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw6.dao.AuthorRepository;
import ru.zotov.hw6.domain.Author;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория авторов
 */
@Service
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
     * @param author автор
     */
    @Override
    public void delete(Author author) {
        em.remove(author);
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

    /**
     * Поиск автора по фио
     *
     * @param fio фио автора
     * @return автор
     */
    @Override
    public List<Author> findByFio(String fio) {
        return em.createQuery("select a from Author a " +
                        "where lower(a.fio) like concat(lower(:fio),'%')", Author.class)
                .setParameter("fio", fio)
                .getResultList();
    }
}
