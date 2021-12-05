package ru.zotov.hw13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.zotov.hw13.dao.UserLibraryRepository;
import ru.zotov.hw13.security.JwtTokenProvider;
import ru.zotov.hw13.service.UserLibraryService;

import java.util.Optional;

/**
 * @author Created by ZotovES on 04.12.2021
 * Реализация сервиса управления пользователями
 */
@Service
@RequiredArgsConstructor
public class UserLibraryServiceImpl implements UserLibraryService {
    private final UserLibraryRepository userLibraryRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    /**
     * Аутентификация пользователя
     *
     * @param userName имя пользователя
     * @param password пароль
     * @return токена
     */
    @Override
    public Optional<String> login(String userName, String password) {
        return userLibraryRepository.findByUsername(userName)
                .filter(user -> passwordEncoder.matches(password, user.getPassword()))
                .map(user -> jwtTokenProvider.generateToken(user.getId(), user.getUsername()));
    }
}
