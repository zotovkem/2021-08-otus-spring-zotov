package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import ru.otus.spring.config.PropertyService;
import ru.otus.spring.service.QuestionService;

import java.util.Scanner;

/**
 * @author Created by ZotovES on 29.08.2021
 */
@Configuration
@ComponentScan
@PropertySource("classpath:application.properties")
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuestionService questionService = context.getBean(QuestionService.class);
        PropertyService propertyService = context.getBean(PropertyService.class);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please write you name: ");
        String userName = scanner.nextLine().trim();

        Integer countRightAnswerConsole = questionService.getCountRightAnswerConsole();

        String resultTest = countRightAnswerConsole > propertyService.getCountRightAnswer() ? "passed" : "failed";

        System.out.printf("Dear %s, count right answer %s. Test %s.%n", userName, countRightAnswerConsole, resultTest);
    }
}
