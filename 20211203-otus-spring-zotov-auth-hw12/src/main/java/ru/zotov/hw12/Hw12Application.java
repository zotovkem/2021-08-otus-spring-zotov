package ru.zotov.hw12;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class Hw12Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw12Application.class, args);
    }
}
