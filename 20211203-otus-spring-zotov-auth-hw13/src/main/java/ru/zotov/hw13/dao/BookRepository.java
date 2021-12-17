package ru.zotov.hw13.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import ru.zotov.hw13.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Override
    @PostFilter("hasPermission(filterObject, 'READ') or hasPermission(filterObject, 'ADMINISTRATION')")
    List<Book> findAll();

    @Override
    @PostAuthorize("hasPermission(returnObject, 'READ')")
    Book getById(Long id);

    @Override
    @PreAuthorize("hasAnyRole('ADMIN')")
    <S extends Book> S save(S entity);
}
