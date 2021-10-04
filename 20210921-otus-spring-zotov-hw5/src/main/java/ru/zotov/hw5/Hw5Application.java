package ru.zotov.hw5;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class Hw5Application {

    public static void main(String[] args) throws SQLException {
        Console.main(args);
        SpringApplication.run(Hw5Application.class, args);
    }

}
