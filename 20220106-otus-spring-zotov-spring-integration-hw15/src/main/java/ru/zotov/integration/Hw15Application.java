package ru.zotov.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.zotov.integration.service.AnalystService;

@SpringBootApplication
public class Hw15Application {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Hw15Application.class, args);
        AnalystService analystService = context.getBean(AnalystService.class);

        while (true) {
            analystService.createEpic();
            Thread.sleep(10000);
        }

    }

}
