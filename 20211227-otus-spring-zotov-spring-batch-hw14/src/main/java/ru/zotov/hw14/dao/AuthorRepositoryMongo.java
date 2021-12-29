package ru.zotov.hw14.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.zotov.hw14.domain.AuthorMongo;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 04.10.2021
 * Репозиторий Авторов книг
 */
public interface AuthorRepositoryMongo extends MongoRepository<AuthorMongo, String>, AuthorRepositoryMongoCustom {
    /**
     * Поиск автора по фио
     *
     * @param fio фио автора
     * @return автор
     */
    List<AuthorMongo> findByFio(String fio);

    /**
     * Поиск авторов по списку ид
     *
     * @param ids список ид
     * @return список авторов
     */
    Set<AuthorMongo> findByIdIn(List<String> ids);
}
