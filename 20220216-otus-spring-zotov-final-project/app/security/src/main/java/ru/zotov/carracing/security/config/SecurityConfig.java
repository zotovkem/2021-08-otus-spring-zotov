package ru.zotov.carracing.security.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import ru.zotov.carracing.security.filter.UserIdFilter;

/**
 * @author Created by ZotovES on 28.07.2021
 * Конфиг настройки безопасности
 */
@ConditionalOnProperty(prefix = "app.security", name = "enabled", havingValue = "true", matchIfMissing = true)
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String METRICS_ENDPOINT = "/metrics/**";
    private static final String AUTH_ENDPOINT = "/auth/**";
    private static final String SWAGGER_ENDPOINT = "/swagger-ui/**";
    private static final String API_DOCS_ENDPOINT = "/v2/api-docs/**";
    protected static final String SWAGGER_RESOURCES = "/swagger-resources/**";

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
                .addFilterBefore(new UserIdFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(AUTH_ENDPOINT, METRICS_ENDPOINT,SWAGGER_ENDPOINT,SWAGGER_RESOURCES,API_DOCS_ENDPOINT).permitAll()
                .anyRequest().authenticated();
    }
}
