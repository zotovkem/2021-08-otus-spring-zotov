package ru.zotov.hw13.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Created by ZotovES on 03.12.2021
 * Пользователь
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "user")
public class UserLibrary {
    @Id
    private String id;
    /**
     * Имя пользователя
     */
    private String username;
    /**
     * Пароль
     */
    private String password;
}
