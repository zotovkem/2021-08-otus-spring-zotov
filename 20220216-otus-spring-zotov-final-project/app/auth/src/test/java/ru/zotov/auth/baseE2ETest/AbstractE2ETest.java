package ru.zotov.auth.baseE2ETest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.zotov.auth.CarRacingAuthApp;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 25.02.2021
 * Абстрактный класс для E2E тестов. Поднимает контекст Spring для полной проверки приложения.
 */
@WithMockUser(value = "testPlayer")
@SpringBootTest(classes = CarRacingAuthApp.class)
@AutoConfigureMockMvc
@Transactional(rollbackFor = Exception.class)
@ContextConfiguration(initializers = {AbstractE2ETest.Initializer.class})
public abstract class AbstractE2ETest {
    protected static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    @SuppressWarnings("rawtypes")
    @ClassRule
    protected static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12")
            .withDatabaseName("carracing-otus")
            .withUsername("sa")
            .withPassword("sa");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgreSQLContainer.start();
            TestPropertyValues.of(
                    "spring.datasource.url=" + postgreSQLContainer.getJdbcUrl(),
                    "spring.datasource.username=" + postgreSQLContainer.getUsername(),
                    "spring.datasource.password=" + postgreSQLContainer.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    /**
     * Мокаем kafka
     */
    @MockBean
    protected KafkaTemplate<String, Object>  kafkaTemplate;

    /**
     * Конфигурация с замоканным Redis
     */
    @Configuration
    static class Config
    {
        @Bean
        @SuppressWarnings("unchecked")
        public RedisSerializer<Object> defaultRedisSerializer()
        {
            return mock(RedisSerializer.class);
        }


        @Bean
        public RedisConnectionFactory connectionFactory()
        {
            RedisConnectionFactory factory = mock(RedisConnectionFactory.class);
            RedisConnection connection = mock(RedisConnection.class);
            when(factory.getConnection()).thenReturn(connection);

            return factory;
        }
    }
}
