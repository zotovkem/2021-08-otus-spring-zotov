package ru.zotov.hw16.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.zotov.hw16.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
@RepositoryRestResource(path = "book")
public interface BookRepository extends MongoRepository<Book, String>, BookRepositoryCustom {
    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    @Query(value = "{'name': { $regex: :#{#name}, $options: 'i' }}")
    List<Book> findByName(@Param("name") String name);

    /**
     * Поиск книг с не цензурными словами в комментариях
     *
     * @param name не цензурное слово
     */
    @Query(value = "{'comments.content': { $regex: :#{#name}, $options: 'i' }}")
    List<Book> findByCommentContent(String name);
}
