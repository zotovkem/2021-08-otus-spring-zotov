package ru.zotov.hw8.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.zotov.hw8.domain.Author;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Авторов книг
 */
@Repository
public interface AuthorRepository extends MongoRepository<Author, String> {
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
