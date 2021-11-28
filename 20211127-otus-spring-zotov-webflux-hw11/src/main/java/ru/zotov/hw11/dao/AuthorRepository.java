package ru.zotov.hw11.dao;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import ru.zotov.hw11.domain.Author;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Авторов книг
 */
public interface AuthorRepository extends ReactiveMongoRepository<Author, String>, AuthorRepositoryCustom {
    /**
     * Поиск автора по фио
     *
     * @param fio фио автора
     * @return автор
     */
    List<Author> findByFio(String fio);

    /**
     * Поиск авторов по списку ид
     *
     * @param ids список ид
     * @return список авторов
     */
    Set<Author> findByIdIn(List<String> ids);
}
