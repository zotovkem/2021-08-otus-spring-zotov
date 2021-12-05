package ru.zotov.hw13.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Модель пользователя для безопасности
 */
@AllArgsConstructor
public class JwtUser implements UserDetails {
    /**
     * Ид
     */
    private final String id;
    /**
     * Имя пользователя
     */
    private final String userName;
    /**
     * Пароль
     */
    private final String password;
    /**
     * Список разрешений пользователя
     */
    private final Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    public String getId() {
        return id;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
