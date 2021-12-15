package ru.zotov.hw13.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.hw13.domain.UserLibrary;

import java.util.Optional;

/**
 * @author Created by ZotovES on 04.12.2021
 * Репозиторий пользователей
 */
public interface UserLibraryRepository extends JpaRepository<UserLibrary, Long> {
    /**
     * Поиск пользователя по имени
     *
     * @param userName имя пользователя
     * @return пользователь
     */
    Optional<UserLibrary> findByUsername(String userName);
}
