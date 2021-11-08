package ru.zotov.hw8.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zotov.hw8.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
@Repository
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    List<Book> findByName(@Param("name") String name);

    /**
     * Поиск по наименованию жанра
     *
     * @param name наименование жанра
     * @return список книг
     */
    @Query(value = "{'genres.name': { $regex: :#{#name} }}")
    List<Book> findByGenreName(@Param("name") String name);

    /**
     * Поиск по ФИО автора книги
     *
     * @param fio ФИО автора
     * @return список книг
     */
    @Query(value = "{'authors.fio': { $regex: :#{#fio} }}")
    List<Book> findByAuthorFio(@Param("fio") String fio);
}
