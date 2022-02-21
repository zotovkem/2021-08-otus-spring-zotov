package ru.zotov.auth.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.lang.NonNull;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.auth.entity.Player;
import ru.zotov.auth.entity.UserTokenInfo;
import ru.zotov.auth.repo.PlayerRepo;
import ru.zotov.auth.repo.UserTokenInfoRepo;
import ru.zotov.auth.service.PlayerService;
import ru.zotov.carracing.common.constant.Constants;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.event.CreatePlayerEvent;
import ru.zotov.carracing.event.SendMailEvent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Created by ZotovES on 28.08.2021
 * Реализация сервиса управления аккаунтом игрока
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PlayerServiceImpl implements PlayerService {
    private final PlayerRepo playerRepo;
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final Mapper mapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserTokenInfoRepo userTokenInfoRepo;

    /**
     * Создание аккаунта
     *
     * @param player аккаунт
     * @return аккаунт
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    @Cacheable(value = "players-cache", key = "#player.email")
    public Player createPlayer(@NonNull Player player) {
        playerRepo.findByEmail(player.getEmail().toLowerCase())
                .ifPresent(p -> {
                    throw new IllegalArgumentException("Email is busy");
                });

        player.setProfileId(UUID.randomUUID());
        String rawPassword = generateAuthCode();
        log.info("Access mail code " + rawPassword);
        player.setPassword(passwordEncoder.encode(rawPassword));
        player.setEmail(player.getEmail().toLowerCase());

        kafkaTemplate.send(Constants.KAFKA_SEND_MAIL_TOPIC, getMessage(player.getEmail(), rawPassword));
        CreatePlayerEvent playerEvent = mapper.map(player, CreatePlayerEvent.class);
        kafkaTemplate.send(Constants.KAFKA_PLAYER_TOPIC, playerEvent);
        kafkaTemplate.send(Constants.KAFKA_CREATE_PROFILE_TOPIC, playerEvent);
        return playerRepo.save(player);
    }

    /**
     * Восстановить пароль
     *
     * @param email почта
     * @return аккаунт
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<Player> recoveryPassword(@NonNull String email) {
        Optional<Player> player = findByEmail(email.toLowerCase());
        player.ifPresent(p -> {
            String rawPassword = generateAuthCode();
            p.setPassword(passwordEncoder.encode(rawPassword));

            kafkaTemplate.send(Constants.KAFKA_SEND_MAIL_TOPIC, getMessage(p.getEmail(), rawPassword));
        });
        return player;
    }

    /**
     * Обновить токен
     *
     * @param refreshToken токен
     * @return информация о игроке
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<UserTokenInfo> refreshToken(@NonNull String refreshToken) {
        Optional<UserTokenInfo> token = Optional.ofNullable(userTokenInfoRepo.findById(refreshToken)
                .orElseGet(() -> playerRepo.findByEmail(jwtTokenProvider.getEmail(refreshToken))
                        .map(this::buildUserTokenInfo)
                        .orElse(null)));

        token.ifPresent(t -> {
            t.setRefreshToken(jwtTokenProvider.createRefreshToken(t.getEmail()));
            t.setToken(jwtTokenProvider.createToken(t.getNickName(), t.getEmail(), t.getUserId()));
            userTokenInfoRepo.save(t);
            userTokenInfoRepo.deleteById(refreshToken);
        });

        return token;
    }

    /**
     * Логин игрока
     *
     * @param email    почта
     * @param password пароль
     * @return токен
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Optional<UserTokenInfo> login(@NonNull String email, @NonNull String password) {
        Optional<Player> player = findByEmail(email.toLowerCase())
                .filter(user -> passwordEncoder.matches(password, user.getPassword()));
        player.map(Player::getProfileId)
                .map(UUID::toString)
                .flatMap(userTokenInfoRepo::findByUserId)
                .ifPresent(userTokenInfoRepo::delete);

        Optional<UserTokenInfo> userTokenInfo = player.map(this::buildUserTokenInfo);
        userTokenInfo.ifPresent(userTokenInfoRepo::save);
        return userTokenInfo;
    }

    /**
     * Поиск аккаунта по почте
     *
     * @param email почта
     * @return аккаунт
     */
    @Override
    @CachePut(value = "players-cache", key = "#email")
    @Transactional(readOnly = true)
    public Optional<Player> findByEmail(@NonNull String email) {
        return playerRepo.findByEmail(email);
    }

    private SendMailEvent getMessage(String email, String accessCode) {
        return SendMailEvent.builder()
                .email(email)
                .messageText("Your are register code " + accessCode)
                .build();
    }

    /**
     * Сгенерировать код для подтверждения почты
     *
     * @return код
     */
    private String generateAuthCode() {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            str.append((int) (Math.random() * 10));
        }
        return str.toString();
    }

    /**
     * Маппинг модели аккаунта игрока в информацию о пользователе
     *
     * @param player аккаунт
     * @return информация о пользователи
     */
    private UserTokenInfo buildUserTokenInfo(Player player) {
        return UserTokenInfo.builder()
                .userId(player.getProfileId().toString())
                .nickName(player.getNickname())
                .email(player.getEmail())
                .refreshToken(jwtTokenProvider.createRefreshToken(player.getEmail()))
                .token(jwtTokenProvider.createToken(player.getNickname(), player.getEmail(), player.getProfileId().toString()))
                .password(player.getPassword())
                .build();
    }
}
