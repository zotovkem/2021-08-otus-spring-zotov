package ru.zotov.hw13;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class Hw13Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw13Application.class, args);
    }
}
