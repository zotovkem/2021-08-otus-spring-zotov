package ru.zotov.hw16.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.zotov.hw16.domain.Genre;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Жанров книг
 */
@RepositoryRestResource(path = "genre")
public interface GenreRepository extends MongoRepository<Genre, String>, GenreRepositoryCustom {
    /**
     * Поиск жанров по списку ид
     *
     * @param ids список ид
     * @return список жанров
     */
    Set<Genre> findByIdIn(List<String> ids);
}
