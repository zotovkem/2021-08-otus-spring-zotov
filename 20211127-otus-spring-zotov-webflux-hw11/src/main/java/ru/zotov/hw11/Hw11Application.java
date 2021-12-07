package ru.zotov.hw11;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class Hw11Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw11Application.class, args);
    }
}
