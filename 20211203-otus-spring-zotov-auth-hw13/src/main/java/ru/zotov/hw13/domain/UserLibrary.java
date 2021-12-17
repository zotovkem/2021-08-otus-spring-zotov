package ru.zotov.hw13.domain;

import lombok.*;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Created by ZotovES on 03.12.2021
 * Пользователь
 */
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "user_library")
public class UserLibrary {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Имя пользователя
     */
    @Column(name = "username")
    private String username;
    /**
     * Пароль
     */
    @Column(name = "password")
    private String password;
    /**
     * Роль
     */
    @Column(name = "role")
    private String role;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) {
            return false;
        }
        UserLibrary that = (UserLibrary) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password);
    }
}
