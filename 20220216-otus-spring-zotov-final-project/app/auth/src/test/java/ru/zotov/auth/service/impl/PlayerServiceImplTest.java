package ru.zotov.auth.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.entity.UserTokenInfo;
import ru.zotov.auth.repo.PlayerRepo;
import ru.zotov.auth.repo.UserTokenInfoRepo;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.common.mapper.Mapper;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 19.02.2022
 */
@DisplayName("Тестирование сервиса управления аккаунтом игрока ")
@ExtendWith(MockitoExtension.class)
class PlayerServiceImplTest {
    @Mock private PlayerRepo playerRepo;
    @Mock private KafkaTemplate<String, Object> kafkaTemplate;
    @Mock private Mapper mapper;
    @Mock private BCryptPasswordEncoder passwordEncoder;
    @Mock private JwtTokenProvider jwtTokenProvider;
    @Mock private UserTokenInfoRepo userTokenInfoRepo;
    @InjectMocks private PlayerServiceImpl playerService;

    @Test
    @DisplayName("Создание аккаунта")
    void createPlayerTest() {
        String email = "test@mail.ru";
        Player player = Player.builder()
                .email(email)
                .build();
        when(playerRepo.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodePass");
        when(playerRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Player result = playerService.createPlayer(player);

        assertThat(result).isNotNull().satisfies(p -> {
            assertThat(p.getPassword()).isNotNull();
            assertThat(p.getProfileId()).isNotNull();
            assertThat(p.getEmail()).isNotNull().isEqualTo(email);
        });

        verify(playerRepo).findByEmail(anyString());
        verify(passwordEncoder).encode(anyString());
        verify(playerRepo).save(any());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_SEND_MAIL_TOPIC), any());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_PLAYER_TOPIC), any());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_CREATE_PROFILE_TOPIC), any());
    }

    @Test
    @DisplayName("Создание аккаунта c уже имеющимся email")
    void createPlayerDoubleEmailTest() {
        String email = "test@mail.ru";
        Player player = Player.builder()
                .email(email)
                .build();
        when(playerRepo.findByEmail(anyString())).thenReturn(Optional.of(player));

        var exception = assertThrows(IllegalArgumentException.class, () -> playerService.createPlayer(player));

        assertThat(exception).hasMessage("Email is busy");

        verify(playerRepo).findByEmail(anyString());
        verify(playerRepo, never()).save(any());
        verify(kafkaTemplate, never()).send(eq(Constants.KAFKA_SEND_MAIL_TOPIC), any());
        verify(kafkaTemplate, never()).send(eq(Constants.KAFKA_PLAYER_TOPIC), any());
        verify(kafkaTemplate, never()).send(eq(Constants.KAFKA_CREATE_PROFILE_TOPIC), any());
    }

    @Test
    @DisplayName("Восстановить пароль")
    void recoveryPasswordTest() {
        String email = "test@mail.ru";
        Player player = Player.builder()
                .email(email)
                .build();
        when(playerRepo.findByEmail(anyString())).thenReturn(Optional.of(player));
        when(passwordEncoder.encode(anyString())).thenReturn("encodePass");

        Optional<Player> result = playerService.recoveryPassword(email);

        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("email", email);

        verify(playerRepo).findByEmail(anyString());
        verify(passwordEncoder).encode(anyString());
        verify(kafkaTemplate).send(eq(Constants.KAFKA_SEND_MAIL_TOPIC), any());
    }

    @Test
    @DisplayName("Восстановить пароль у не существующего email")
    void recoveryPasswordNotFoundEmailTest() {
        String email = "test@mail.ru";
        when(playerRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        Optional<Player> result = playerService.recoveryPassword(email);

        assertThat(result).isEmpty();

        verify(playerRepo).findByEmail(anyString());
        verify(passwordEncoder, never()).encode(anyString());
        verify(kafkaTemplate, never()).send(eq(Constants.KAFKA_SEND_MAIL_TOPIC), any());
    }

    @Test
    @DisplayName("Обновить токен")
    void refreshTokenTest() {
        String newRefreshToken = "newRefreshToken";
        String newToken = "newToken";
        String email = "test@mail.ru";
        String nick = "testNick";
        String userId = UUID.randomUUID().toString();
        UserTokenInfo tokenInfo = UserTokenInfo.builder()
                .userId(userId)
                .nickName(nick)
                .email(email)
                .build();
        when(userTokenInfoRepo.findById(anyString())).thenReturn(Optional.of(tokenInfo));
        when(jwtTokenProvider.createRefreshToken(anyString())).thenReturn(newRefreshToken);
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(jwtTokenProvider.createToken(anyString(), anyString(), anyString())).thenReturn(newToken);
        when(userTokenInfoRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<UserTokenInfo> result = playerService.refreshToken("refreshToken");

        assertThat(result).isPresent().get().satisfies(userTokenInfo -> {
            assertThat(userTokenInfo.getRefreshToken()).isNotNull().isEqualTo(newRefreshToken);
            assertThat(userTokenInfo.getToken()).isNotNull().isEqualTo(newToken);
            assertThat(userTokenInfo.getUserId()).isNotNull().isEqualTo(userId);
            assertThat(userTokenInfo.getNickName()).isNotNull().isEqualTo(nick);
            assertThat(userTokenInfo.getEmail()).isNotNull().isEqualTo(email);
        });

        verify(userTokenInfoRepo).findById(anyString());
        verify(jwtTokenProvider).createRefreshToken(anyString());
        verify(jwtTokenProvider).validateToken(anyString());
        verify(jwtTokenProvider).createToken(anyString(), anyString(), anyString());
        verify(userTokenInfoRepo).save(any());
    }

    @Test
    @DisplayName("Не найден refresh token для обновления ")
    void refreshTokenNotFoundTest() {
        when(jwtTokenProvider.validateToken(anyString())).thenReturn(true);
        when(userTokenInfoRepo.findById(anyString())).thenReturn(Optional.empty());

        Optional<UserTokenInfo> result = playerService.refreshToken("refreshToken");

        assertThat(result).isEmpty();

        verify(jwtTokenProvider).validateToken(anyString());
        verify(userTokenInfoRepo).findById(anyString());
        verify(userTokenInfoRepo, never()).save(any());
    }

    @Test
    @DisplayName("Логин игрока")
    void loginTest() {
        String refreshToken = "RefreshToken";
        String token = "Token";
        String email = "test@mail.ru";
        String nick = "testNick";
        String password = "password";
        UUID uuid = UUID.randomUUID();
        String userId = uuid.toString();
        UserTokenInfo tokenInfo = UserTokenInfo.builder()
                .userId(userId)
                .nickName(nick)
                .refreshToken(refreshToken)
                .token(token)
                .email(email)
                .build();
        Player player = Player.builder()
                .email(email)
                .nickname(nick)
                .profileId(uuid)
                .password(password)
                .build();
        when(playerRepo.findByEmail(anyString())).thenReturn(Optional.of(player));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(true);
        when(userTokenInfoRepo.findByUserId(anyString())).thenReturn(Optional.of(tokenInfo));
        when(userTokenInfoRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(jwtTokenProvider.createRefreshToken(anyString())).thenReturn(refreshToken);
        when(jwtTokenProvider.createToken(anyString(), anyString(), anyString())).thenReturn(token);

        Optional<UserTokenInfo> result = playerService.login(email, password);

        assertThat(result).isPresent().get().satisfies(userTokenInfo -> {
            assertThat(userTokenInfo.getRefreshToken()).isNotNull().isEqualTo(refreshToken);
            assertThat(userTokenInfo.getToken()).isNotNull().isEqualTo(token);
            assertThat(userTokenInfo.getUserId()).isNotNull().isEqualTo(userId);
            assertThat(userTokenInfo.getNickName()).isNotNull().isEqualTo(nick);
            assertThat(userTokenInfo.getEmail()).isNotNull().isEqualTo(email);
        });

        verify(playerRepo).findByEmail(anyString());
        verify(passwordEncoder).matches(anyString(), anyString());
        verify(userTokenInfoRepo).findByUserId(anyString());
        verify(userTokenInfoRepo).save(any());
        verify(userTokenInfoRepo).delete(any());
        verify(jwtTokenProvider).createRefreshToken(anyString());
        verify(jwtTokenProvider).createToken(anyString(), anyString(), anyString());
    }

    @Test
    @DisplayName("Логин игрока с неправильным паролем")
    void loginWrongPasswordTest() {
        String email = "test@mail.ru";
        String nick = "testNick";
        String password = "password";
        UUID uuid = UUID.randomUUID();
        Player player = Player.builder()
                .email(email)
                .nickname(nick)
                .profileId(uuid)
                .password(password)
                .build();
        when(playerRepo.findByEmail(anyString())).thenReturn(Optional.of(player));
        when(passwordEncoder.matches(anyString(), anyString())).thenReturn(false);

        Optional<UserTokenInfo> result = playerService.login(email, password);

        assertThat(result).isEmpty();

        verify(playerRepo).findByEmail(anyString());
        verify(passwordEncoder).matches(anyString(), anyString());
        verify(userTokenInfoRepo,never()).save(any());
    }

    @Test
    @DisplayName("Логин игрока c неверным email")
    void loginWrongEmailTest() {
        String email = "test@mail.ru";
        String password = "password";
        when(playerRepo.findByEmail(anyString())).thenReturn(Optional.empty());

        Optional<UserTokenInfo> result = playerService.login(email, password);

        assertThat(result).isEmpty();

        verify(playerRepo).findByEmail(anyString());
        verify(userTokenInfoRepo, never()).save(any());
    }

    @Test
    @DisplayName("Поиск аккаунта по почте")
    void findByEmailTest() {
        String email = "test@mail.ru";
        String nick = "testNick";
        String password = "password";
        UUID uuid = UUID.randomUUID();
        Player player = Player.builder()
                .email(email)
                .nickname(nick)
                .profileId(uuid)
                .password(password)
                .build();
        when(playerRepo.findByEmail(anyString())).thenReturn(Optional.of(player));

        Optional<Player> result = playerService.findByEmail(email);
        assertThat(result).isPresent().get().usingRecursiveComparison().isEqualTo(player);

        verify(playerRepo).findByEmail(anyString());
    }
}
