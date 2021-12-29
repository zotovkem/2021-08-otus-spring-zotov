package ru.zotov.hw14.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.zotov.hw14.domain.GenreMongo;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Жанров книг
 */
public interface GenreRepositoryMongo extends MongoRepository<GenreMongo, String>, GenreRepositoryMongoCustom {
    /**
     * Поиск жанров по списку ид
     *
     * @param ids список ид
     * @return список жанров
     */
    Set<GenreMongo> findByIdIn(List<String> ids);
}
