package ru.zotov.hw14.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.zotov.hw14.domain.BookMongo;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
public interface BookRepositoryMongo extends MongoRepository<BookMongo, String>, BookRepositoryMongoCustom {
    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    @Query(value = "{'name': { $regex: :#{#name}, $options: 'i' }}")
    List<BookMongo> findByName(@Param("name") String name);
}
