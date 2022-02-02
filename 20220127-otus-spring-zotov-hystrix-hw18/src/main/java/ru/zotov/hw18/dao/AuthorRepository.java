package ru.zotov.hw18.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw18.domain.Author;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Авторов книг
 */
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

}
