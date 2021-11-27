package ru.zotov.hw11.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.zotov.hw11.domain.Genre;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Жанров книг
 */
public interface GenreRepository extends MongoRepository<Genre, String>, GenreRepositoryCustom {
    /**
     * Поиск жанров по списку ид
     *
     * @param ids список ид
     * @return список жанров
     */
    Set<Genre> findByIdIn(List<String> ids);
}
