package ru.zotov.hw18;

import org.junit.ClassRule;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.zotov.hw18.integration.RatingBookFeignClient;

/**
 * @author Created by ZotovES on 25.02.2021
 * Абстрактный класс для E2E тестов. Поднимает контекст Spring для полной проверки приложения.
 */
@SpringBootTest(classes = Hw18Application.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = {AbstractTest.Initializer.class})
public abstract class AbstractTest {
    @MockBean
    protected RatingBookFeignClient ratingBookFeign;

    @ClassRule
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:12")
            .withDatabaseName("test")
            .withUsername("sa")
            .withPassword("sa");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            postgreSQLContainer.start();
        }
    }
}
