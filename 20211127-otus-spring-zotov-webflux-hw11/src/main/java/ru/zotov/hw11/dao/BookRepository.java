package ru.zotov.hw11.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.zotov.hw11.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    @Query(value = "{'name': { $regex: :#{#name}, $options: 'i' }}")
    List<Book> findByName(@Param("name") String name);
}
