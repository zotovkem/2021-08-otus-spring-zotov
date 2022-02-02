package ru.zotov.hw18.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw18.domain.Genre;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Жанров книг
 */
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

}
