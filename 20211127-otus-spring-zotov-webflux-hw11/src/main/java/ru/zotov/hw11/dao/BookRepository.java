package ru.zotov.hw11.dao;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Flux;
import ru.zotov.hw11.domain.Book;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
public interface BookRepository extends ReactiveMongoRepository<Book, String>, BookRepositoryCustom {
    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    @Query(value = "{'name': { $regex: :#{#name}, $options: 'i' }}")
    Flux<Book> findByName(@Param("name") String name);
}
