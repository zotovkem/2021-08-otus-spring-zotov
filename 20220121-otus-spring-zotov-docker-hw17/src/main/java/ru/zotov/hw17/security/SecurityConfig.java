package ru.zotov.hw17.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.zotov.hw17.security.jwt.JwtTokenFilter;
import ru.zotov.hw17.security.jwt.JwtTokenProvider;

/**
 * @author Created by ZotovES on 28.07.2021
 * Конфиг настройки безопасности
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String AUTH_ENDPOINT = "/api/auth/login";
    private static final String ACTUATOR_ENDPOINT = "/actuator/**";
    public static final String ROLE_ADMIN = "ROLE_ADMIN";
    private final JwtTokenProvider jwtTokenProvider;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .addFilterBefore(new JwtTokenFilter(jwtTokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT,ACTUATOR_ENDPOINT).permitAll()
                .anyRequest().authenticated();
    }
}
