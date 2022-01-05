package ru.zotov.hw14.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw14.domain.jpa.BookJpa;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Книг
 */
@Repository
public interface BookRepositoryJpa extends JpaRepository<BookJpa, Long> {
}
