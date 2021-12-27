package ru.zotov.hw13.security.acl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.ehcache.EhCacheFactoryBean;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.access.expression.method.MethodSecurityExpressionHandler;
import org.springframework.security.acls.AclPermissionCacheOptimizer;
import org.springframework.security.acls.AclPermissionEvaluator;
import org.springframework.security.acls.domain.*;
import org.springframework.security.acls.jdbc.BasicLookupStrategy;
import org.springframework.security.acls.jdbc.JdbcMutableAclService;
import org.springframework.security.acls.jdbc.LookupStrategy;
import org.springframework.security.acls.model.PermissionGrantingStrategy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Optional;

import static ru.zotov.hw13.security.SecurityConfig.ROLE_ADMIN;

/**
 * Конфигурация ACL
 */
@Configuration
@RequiredArgsConstructor
public class AclConfig {
    protected static final String ACL_CACHE_NAME = "aclCache";

    private final DataSource dataSource;

    /**
     * Кэш ACL
     */
    @Bean
    public EhCacheBasedAclCache aclCache() {
        return new EhCacheBasedAclCache(
                Objects.requireNonNull(aclEhCacheFactoryBean().getObject()),
                permissionGrantingStrategy(),
                aclAuthorizationStrategy()
        );
    }

    /**
     * Фабрика кэша ACL
     */
    @Bean
    public EhCacheFactoryBean aclEhCacheFactoryBean() {
        EhCacheFactoryBean ehCacheFactoryBean = new EhCacheFactoryBean();
        Optional.ofNullable(aclCacheManager().getObject()).ifPresent(ehCacheFactoryBean::setCacheManager);
        ehCacheFactoryBean.setCacheName(ACL_CACHE_NAME);

        return ehCacheFactoryBean;
    }

    /**
     * Фабрика менеджера кэша
     */
    @Bean
    public EhCacheManagerFactoryBean aclCacheManager() {
        return new EhCacheManagerFactoryBean();
    }

    /**
     * Стратегия разрешений по умолчанию
     */
    @Bean
    public PermissionGrantingStrategy permissionGrantingStrategy() {
        return new DefaultPermissionGrantingStrategy(new ConsoleAuditLogger());
    }

    /**
     * Стратегия авторизации
     */
    @Bean
    public AclAuthorizationStrategy aclAuthorizationStrategy() {
        return new AclAuthorizationStrategyImpl(new SimpleGrantedAuthority(ROLE_ADMIN));
    }

    /**
     * Метод поиска выражения безопасности
     */
    @Bean
    public MethodSecurityExpressionHandler defaultMethodSecurityExpressionHandler() {
        DefaultMethodSecurityExpressionHandler expressionHandler = new DefaultMethodSecurityExpressionHandler();
        AclPermissionEvaluator permissionEvaluator = new AclPermissionEvaluator(aclService());
        expressionHandler.setPermissionEvaluator(permissionEvaluator);
        expressionHandler.setPermissionCacheOptimizer(new AclPermissionCacheOptimizer(aclService()));
        return expressionHandler;
    }

    /**
     * Стратегия поиска прав
     */
    @Bean
    public LookupStrategy lookupStrategy() {
        return new BasicLookupStrategy(dataSource, aclCache(), aclAuthorizationStrategy(), new ConsoleAuditLogger());
    }

    /**
     * Сервис управления ACL
     *
     * @return сервис
     */
    @Bean
    public JdbcMutableAclService aclService() {
        return new JdbcMutableAclService(dataSource, lookupStrategy(), aclCache());
    }
}
