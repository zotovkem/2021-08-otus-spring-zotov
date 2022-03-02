package ru.zotov.auth.service.impl;

import io.jsonwebtoken.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.zotov.auth.entity.UserTokenInfo;
import ru.zotov.auth.exception.JwtAuthenticationException;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.*;


@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secret;

    @Value("${jwt.token.expired}")
    private long validityInMilliseconds;

    @PostConstruct
    protected void init() {
        secret = new String(Base64.getDecoder().decode(secret));
    }

    public String createToken(String username, String email, String profileId) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", Collections.emptyList());
        claims.put("email", email);
        claims.put("userId", profileId);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public String createRefreshToken(String email) {
        Date now = new Date();
        Date validity = new GregorianCalendar(2300, Calendar.FEBRUARY, 1).getTime();
        Claims claims = Jwts.claims().setSubject(email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public UserTokenInfo getUserTokenInfo(String token) {
        Claims body = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return UserTokenInfo.builder()
                .nickName(body.getSubject())
                .email(body.get("email", String.class))
                .userId(body.get("userId", String.class))
                .build();
    }

    public String getEmail(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }


    public String resolveToken(HttpServletRequest req) {
        return req.getHeader("X-TOKEN");
    }

    public boolean validateToken(String token) {
        try {

            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new JwtAuthenticationException("JWT token is expired or invalid");
        }
    }
}
