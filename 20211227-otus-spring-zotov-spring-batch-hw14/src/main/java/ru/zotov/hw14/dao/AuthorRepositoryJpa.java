package ru.zotov.hw14.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw14.domain.jpa.AuthorJpa;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Авторов книг
 */
@Repository
public interface AuthorRepositoryJpa extends JpaRepository<AuthorJpa, Long> {

}
