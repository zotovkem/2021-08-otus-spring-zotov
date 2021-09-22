package ru.otus.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import ru.otus.spring.config.PropertyService;
import ru.otus.spring.service.InputService;
import ru.otus.spring.service.QuestionService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
        QuestionService questionService = context.getBean(QuestionService.class);
        PropertyService propertyService = context.getBean(PropertyService.class);
        InputService inputService = context.getBean(InputService.class);

        System.out.print("Please write you name: ");
        String userName = inputService.getConsoleStrValue();

        Integer countRightAnswerConsole = questionService.getCountRightAnswerConsole();

        String resultTest = countRightAnswerConsole > propertyService.getCountRightAnswer() ? "passed" : "failed";

        System.out.printf("Dear %s, count right answer %s. Test %s.%n", userName, countRightAnswerConsole, resultTest);
    }

}
