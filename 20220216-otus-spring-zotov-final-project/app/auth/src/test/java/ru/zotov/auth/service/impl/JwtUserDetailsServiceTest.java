package ru.zotov.auth.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.service.PlayerService;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 19.02.2022
 */
@DisplayName("Тестирование сервиса детальной информации пользователя")
@ExtendWith(MockitoExtension.class)
class JwtUserDetailsServiceTest {
    @Mock private PlayerService playerService;
    @InjectMocks private JwtUserDetailsService jwtUserDetailsService;

    @Test
    @DisplayName("Поиск пользователя по имени")
    void loadUserByUsernameTest() {
        Player player = Player.builder()
                .id(1L)
                .profileId(UUID.randomUUID())
                .build();
        when(playerService.findByEmail(anyString())).thenReturn(Optional.of(player));

        UserDetails result = jwtUserDetailsService.loadUserByUsername("test");

        assertThat(result).isNotNull().extracting("id").isEqualTo(1L);
        verify(playerService).findByEmail(anyString());
    }

    @Test
    @DisplayName("Не удачный поиск пользователя по имени")
    void loadUserByUsernameNotFoundTest() {
        when(playerService.findByEmail(anyString())).thenReturn(Optional.empty());

        var exception = assertThrows(UsernameNotFoundException.class, () -> jwtUserDetailsService.loadUserByUsername("test"));

        assertThat(exception.getMessage()).isEqualTo("User with username: test not found");
        verify(playerService).findByEmail(anyString());
    }
}
