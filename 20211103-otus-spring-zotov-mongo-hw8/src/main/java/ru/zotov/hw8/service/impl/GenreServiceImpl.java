package ru.zotov.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.zotov.hw8.dao.GenreRepository;
import ru.zotov.hw8.domain.Genre;
import ru.zotov.hw8.service.GenreService;

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
     * Удалить по ид
     *
     * @param id ид жанра
     */
    @Override
    public void deleteById(String id) {
        genreRepository.deleteById(id);
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
}
