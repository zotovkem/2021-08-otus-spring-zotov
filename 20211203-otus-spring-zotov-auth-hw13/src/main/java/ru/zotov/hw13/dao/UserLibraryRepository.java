package ru.zotov.hw13.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import ru.zotov.hw13.domain.UserLibrary;

import java.util.Optional;

/**
 * @author Created by ZotovES on 04.12.2021
 * Репозиторий пользователей
 */
public interface UserLibraryRepository extends MongoRepository<UserLibrary, String> {
    /**
     * Поиск пользователя по имени
     *
     * @param userName имя пользователя
     * @return пользователь
     */
    Optional<UserLibrary> findByUsername(String userName);
}
