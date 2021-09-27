package ru.otus.spring;

import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.context.annotation.*;
import ru.otus.spring.service.TestService;

/**
 * @author Created by ZotovES on 29.08.2021
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {
    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(Main.class);
    }

    @Bean
    public SmartInitializingSingleton importProcessor(TestService testService) {
        return testService::start;

    }
}
