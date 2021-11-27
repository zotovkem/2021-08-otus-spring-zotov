package ru.zotov.hw11.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw11.dao.GenreRepository;
import ru.zotov.hw11.domain.Genre;
import ru.zotov.hw11.exception.ConstrainDeleteException;
import ru.zotov.hw11.service.GenreService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Created by ZotovES on 28.10.2021
 * Реализация сервиса жанров
 */
@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;

    /**
     * Сохранить жанр
     *
     * @param genre жанр
     * @return жанр
     */
    @Override
    public Genre save(Genre genre) {
        return genreRepository.save(genre);
    }

    /**
     * Получить жанр по ид
     *
     * @param id ид
     * @return жанр
     */
    @Override
    public Optional<Genre> findById(String id) {
        return genreRepository.findById(id);
    }

    /**
     * Получить все жанры
     *
     * @return список жанров
     */
    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    /**
     * Поиск жанров по списку ид
     *
     * @param ids список ид
     * @return список жанров
     */
    @Override
    public Set<Genre> findByIdIn(List<String> ids) {
        return genreRepository.findByIdIn(ids);
    }

    /**
     * Удалить жанры по списку ид
     *
     * @param ids список ид жанра
     */
    @Override
    @Transactional
    public void deleteByListIds(List<String> ids) throws ConstrainDeleteException {
        genreRepository.deleteWithConstraintsByIds(ids);
    }
}
