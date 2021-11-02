package ru.zotov.hw7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw7.dao.GenreRepository;
import ru.zotov.hw7.domain.Genre;
import ru.zotov.hw7.service.GenreService;

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
     * Удалить по ид
     *
     * @param id ид жанра
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(genreRepository::delete);
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
     * Найти жанры по наименованию
     *
     * @param genreName наименование жанра
     * @return список жанров
     */
    @Override
    public List<Genre> findByName(String genreName) {
        return genreRepository.findByName(genreName);
    }
}
