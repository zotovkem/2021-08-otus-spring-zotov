package ru.zotov.hw11.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.zotov.hw11.domain.Genre;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Жанров книг
 */
public interface GenreRepository extends ReactiveMongoRepository<Genre, String>, GenreRepositoryCustom {
    /**
     * Поиск жанров по списку ид
     *
     * @param ids список ид
     * @return список жанров
     */
    Flux<Genre> findByIdIn(List<String> ids);
}
