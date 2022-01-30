package ru.zotov.hw17.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw17.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    List<Book> findAll();
}
