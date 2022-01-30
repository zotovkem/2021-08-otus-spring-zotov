package ru.zotov.hw18;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class Hw18Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw18Application.class, args);
    }
}
