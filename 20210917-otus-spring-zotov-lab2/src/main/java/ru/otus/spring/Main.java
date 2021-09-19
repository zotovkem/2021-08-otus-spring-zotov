package ru.otus.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.service.QuestionService;

import java.util.Scanner;

/**
 * @author Created by ZotovES on 29.08.2021
 */
@Configuration
@ComponentScan
public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(Main.class);
        QuestionService questionService = context.getBean(QuestionService.class);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Please write you name: ");
        String userName = scanner.nextLine().trim();

        Integer countRightAnswerConsole = questionService.getCountRightAnswerConsole();

        String resultTest = countRightAnswerConsole > 1 ? "passed" : "failed";

        System.out.printf("Dear %s, count right answer %s. Test %s.%n", userName, countRightAnswerConsole, resultTest);
    }
}
