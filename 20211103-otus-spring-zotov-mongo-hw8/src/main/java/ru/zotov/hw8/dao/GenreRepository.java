package ru.zotov.hw8.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.zotov.hw8.domain.Genre;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Жанров книг
 */
public interface GenreRepository extends MongoRepository<Genre, String> {
    /**
     * Найти жанры по наименованию
     *
     * @param genreName наименование жанра
     * @return список жанров
     */
    List<Genre> findByName(String genreName);

    /**
     * Поиск жанров по списку ид
     *
     * @param ids список ид
     * @return список жанров
     */
    Set<Genre> findByIdIn(List<String> ids);
}
