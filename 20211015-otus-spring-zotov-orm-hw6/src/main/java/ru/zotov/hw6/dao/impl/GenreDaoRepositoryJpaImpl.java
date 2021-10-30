package ru.zotov.hw6.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw6.dao.GenreRepository;
import ru.zotov.hw6.domain.Genre;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Реализация репозитория жанров
 */
@Service
@RequiredArgsConstructor
public class GenreDaoRepositoryJpaImpl implements GenreRepository {
    @PersistenceContext
    private final EntityManager em;

    /**
     * Создать
     *
     * @param genre жанр
     * @return жанр
     */
    @Override
    public Genre create(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
            return genre;
        }
        return update(genre);
    }

    /**
     * Редактировать
     *
     * @param genre жанр
     * @return жанр
     */
    @Override
    public Genre update(Genre genre) {
        return em.merge(genre);
    }

    /**
     * Получить жанр по ид
     *
     * @param id ид
     * @return жанр
     */
    @Override
    public Optional<Genre> findById(Long id) {
        return Optional.ofNullable(em.find(Genre.class, id));
    }

    /**
     * Удалить по ид
     *
     * @param genre жанр
     */
    @Override
    public void delete(Genre genre) {
        em.remove(genre);
    }

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    @Override
    public List<Genre> findAll() {
        return em.createQuery("select g from Genre g", Genre.class).getResultList();
    }

    /**
     * Найти жанры по наименованию
     *
     * @param genreName наименование жанра
     * @return список жанров
     */
    @Override
    public List<Genre> findByName(String genreName) {
        return em.createQuery("select g from Genre g " +
                        "where lower(g.name) like concat(lower(:genre),'%')", Genre.class)
                .setParameter("genre", genreName)
                .getResultList();
    }
}
