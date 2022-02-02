package ru.zotov.hw18.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw18.dao.GenreRepository;
import ru.zotov.hw18.domain.Genre;
import ru.zotov.hw18.service.GenreService;

import java.util.List;
import java.util.Optional;

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
    public Optional<Genre> findById(Long id) {
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
    public List<Genre> findByIdIn(List<Long> ids) {
        return genreRepository.findAllById(ids);
    }

    /**
     * Удалить жанры по списку ид
     *
     * @param ids список ид жанра
     */
    @Override
    public void deleteByListIds(List<Long> ids) {
        genreRepository.deleteAllByIdInBatch(ids);
    }
}
