package ru.zotov.hw17.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 04.12.2021
 * Дто для получения пары логин пароль
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    /**
     * Имя пользователя
     */
    private String username;
    /**
     * Пароль
     */
    private String password;
}
