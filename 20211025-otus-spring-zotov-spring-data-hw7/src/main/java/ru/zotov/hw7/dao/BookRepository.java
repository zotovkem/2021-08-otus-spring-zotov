package ru.zotov.hw7.dao;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.zotov.hw7.domain.Book;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @EntityGraph(value = "book-graph")
    List<Book> findAll();

    @Override
    @EntityGraph(value = "book-graph")
    Optional<Book> findById(Long aLong);

    /**
     * Найти книгу по наименованию
     *
     * @param name наименование книги
     * @return список книг
     */
    @EntityGraph(value = "book-graph")
    @Query(value = "select b from Book b " +
            "where lower(b.name) like concat(lower(:name),'%')")
    List<Book> findByName(@Param("name") String name);

    /**
     * Найти книги по ФИО автора
     *
     * @param authorFio фио автора книги
     * @return Список книг
     */
    @EntityGraph(value = "book-graph")
    @Query(value = "select b from Book b " +
            "join fetch b.authors a " +
            "where lower(a.fio) like concat(lower(:fio),'%')")
    List<Book> findByAuthorFio(@Param("fio") String authorFio);

    /**
     * Найти книги по жанру
     *
     * @param genreName жанр
     * @return список книг
     */
    @EntityGraph(value = "book-graph")
    @Query(value = "select b from Book b " +
            "join fetch b.genres g " +
            "where lower(g.name) like concat(lower(:genre),'%')")
    List<Book> findByGenreName(@Param("genre") String genreName);
}
