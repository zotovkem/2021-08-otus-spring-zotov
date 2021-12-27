package ru.zotov.hw11.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import ru.zotov.hw11.domain.Author;

import java.util.List;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Авторов книг
 */
public interface AuthorRepository extends ReactiveMongoRepository<Author, String>, AuthorRepositoryCustom {
    /**
     * Поиск авторов по списку ид
     *
     * @param ids список ид
     * @return список авторов
     */
    Flux<Author> findByIdIn(List<String> ids);
}
