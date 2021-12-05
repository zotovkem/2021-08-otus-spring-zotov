package ru.zotov.hw13.security;

import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Optional;

/**
 * @author Created by ZotovES on 30.07.2021
 * Вспомогательный сервис для работы с JWT токеном
 */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider {
    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    private final UserDetailsService userDetailsService;

    /**
     * Получить аутентификация из токена
     *
     * @param token токен
     * @return аутентификация
     */
    public Authentication getAuthentication(String token) {
        return Optional.ofNullable(token)
                .map(t -> userDetailsService.loadUserByUsername(getUsername(t)))
                .map(carxUserDetails -> new UsernamePasswordAuthenticationToken(carxUserDetails, "",
                        carxUserDetails.getAuthorities()))
                .orElseThrow(() -> new UsernameNotFoundException("Not found user by name"));
    }

    /**
     * Сгенерировать токен для пользователя
     *
     * @param userName имя пользователя
     * @param id       ид пользователя
     * @return токен
     */
    public String generateToken(String id, String userName) {
        Claims claims = Jwts.claims().setSubject(userName);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setId(id)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    /**
     * Проверить токен
     *
     * @param token токен
     * @return результат проверки
     */
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            return !claims.getBody().getExpiration().before(new Date());
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    /**
     * Получить ид пользователя из токена
     *
     * @param token токен
     * @return ид
     */
    private String getUserId(String token) {
        return Optional.ofNullable(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().get("orig", String.class))
                .orElseThrow(() -> new JwtException("Token invalid"));
    }

    /**
     * Получить имя пользователя из токена
     *
     * @param token токен
     * @return имя пользователя
     */
    private String getUsername(String token) {
        return Optional.ofNullable(Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject())
                .orElseThrow(() -> new JwtException("Token invalid"));
    }
}
