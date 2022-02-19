package ru.zotov.auth.service;

import org.springframework.data.util.Pair;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.entity.UserTokenInfo;

import java.util.Optional;

/**
 * @author Created by ZotovES on 28.08.2021
 * Сервис управления аккаунтом игрока
 */
public interface PlayerService {
    /**
     * Создание аккаунта
     * @param player аккаунт
     * @return аккаунт
     */
    Player createPlayer(@NonNull Player player);

    /**
     * Логин игрока
     * @param email почта
     * @param password пароль
     * @return токен
     */
    Optional<UserTokenInfo> login(@NonNull String email, @NonNull String password);

    /**
     * Поиск аккаунта по почте
     * @param email почта
     * @return аккаунт
     */
    Optional<Player> findByEmail(@NonNull String email);

    /**
     * Обновить токен
     * @param refreshToken токен
     * @return информация о игроке
     */
    Optional<UserTokenInfo> refreshToken(@NonNull String refreshToken);

    /**
     * Восстановить пароль
     * @param email почта
     * @return аккаунт
     */
    Optional<Player> recoveryPassword(@NonNull String email);
}
