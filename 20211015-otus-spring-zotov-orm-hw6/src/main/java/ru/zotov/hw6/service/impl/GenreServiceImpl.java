package ru.zotov.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw6.dao.GenreRepository;
import ru.zotov.hw6.domain.Genre;
import ru.zotov.hw6.service.GenreService;

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
     * Создать
     *
     * @param genre жанр
     * @return жанр
     */
    @Override
    public Genre create(Genre genre) {
        return genreRepository.create(genre);
    }

    /**
     * Редактировать
     *
     * @param genre жанр
     * @return жанр
     */
    @Override
    public Genre update(Genre genre) {
        return genreRepository.update(genre);
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
