package ru.zotov.hw12.service;

import java.util.Optional;

/**
 * @author Created by ZotovES on 04.12.2021
 * Сервис управления пользователями
 */
public interface UserLibraryService {
    /**
     * Аутентификация пользователя
     *
     * @param userName имя пользователя
     * @param password пароль
     * @return токена
     */
    Optional<String> login(String userName, String password);
}
