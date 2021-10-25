package ru.zotov.hw6.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import ru.zotov.hw6.dao.BookRepository;
import ru.zotov.hw6.domain.Book;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория книг
 */
@Repository
@RequiredArgsConstructor
public class BookRepositoryJpaImpl implements BookRepository {
    @PersistenceContext
    private final EntityManager em;

    /**
     * Добавить книгу
     *
     * @param book книга
     */
    @Override
    public Book create(Book book) {
        if (book.getId() == null) {
            em.persist(book);
            return book;
        }
        return update(book);
    }

    /**
     * Редактировать книгу
     *
     * @param book книга
     * @return книга
     */
    @Override
    public Book update(Book book) {
        return em.merge(book);
    }

    /**
     * Удалить книгу
     *
     * @param id ид
     */
    @Override
    public void deleteById(Long id) {
        em.createQuery("delete from Book where id = :id")
                .setParameter("id", id)
                .executeUpdate();
    }

    /**
     * Получить книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @Override
    public Optional<Book> findById(Long id) {
        return Optional.ofNullable(em.find(Book.class, id));
    }

    /**
     * Получить все книги
     *
     * @return список книг
     */
    @Override
    public List<Book> findAll() {
        return em.createQuery("select distinct b from Book b ", Book.class)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("comment-graph"))
                .getResultList();
    }

    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    @Override
    public List<Book> findByName(String name) {
        return em.createQuery("select b from Book b " +
                        "where lower(b.name) like concat(lower(:name),'%')", Book.class)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("comment-graph"))
                .setParameter("name", name)
                .getResultList();
    }

    /**
     * Найти книги по ФИО автора
     *
     * @param authorFio фио автора книги
     * @return Список книг
     */
    @Override
    public List<Book> findByAuthorFio(String authorFio) {
        return em.createQuery("select b from Book b " +
                        "join fetch b.authors a " +
                        "where lower(a.fio) like concat(lower(:fio),'%')", Book.class)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("comment-graph"))
                .setParameter("fio", authorFio)
                .getResultList();
    }

    /**
     * Найти книги по жанру
     *
     * @param genreName жанр
     * @return список книг
     */
    @Override
    public List<Book> findByGenreName(String genreName) {
        return em.createQuery("select b from Book b " +
                        "join fetch b.genres g " +
                        "where lower(g.name) like concat(lower(:genre),'%')", Book.class)
                .setParameter("genre", genreName)
                .setHint("javax.persistence.fetchgraph", em.getEntityGraph("comment-graph"))
                .getResultList();
    }
}
