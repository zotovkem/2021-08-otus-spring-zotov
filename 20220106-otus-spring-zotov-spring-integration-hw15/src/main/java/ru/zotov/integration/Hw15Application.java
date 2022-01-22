package ru.zotov.integration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.zotov.integration.service.impl.AnalystServiceImpl;

import java.util.concurrent.ForkJoinPool;

@SpringBootApplication
public class Hw15Application {

    public static void main(String[] args) throws InterruptedException {
        ConfigurableApplicationContext context = SpringApplication.run(Hw15Application.class, args);
        AnalystServiceImpl analystService = context.getBean(AnalystServiceImpl.class);

        int i = 1;
        while (true) {
            analystService.createEpic("Epic " + i);

            Thread.sleep(10000);
            i++;
        }
    }
}
