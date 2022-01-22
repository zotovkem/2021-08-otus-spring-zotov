package ru.zotov.hw16;

import com.github.cloudyrock.spring.v5.EnableMongock;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMongock
@SpringBootApplication
public class Hw16Application {

    public static void main(String[] args) {
        SpringApplication.run(Hw16Application.class, args);
    }
}
