package ru.otus.spring.config;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.TestService;

/**
 * @author Created by ZotovES on 27.09.2021
 * Конфиг приложения
 */
@Configuration
public class ApplicationConfig {
    /**
     * Бин автозапуска тестирования
     *
     * @param testService Сервис тестирования
     */
    @Bean
    public SmartInitializingSingleton importProcessor(TestService testService) {
        return testService::start;

    }
}
