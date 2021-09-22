package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.PropertyService;
import ru.otus.spring.service.InputService;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.TestService;

/**
 * @author Created by ZotovES on 22.09.2021
 * Реализация сервиса тестирования
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionService questionService;
    private final PropertyService propertyService;
    private final InputService inputService;

    /**
     * Старт теста
     */
    @Override
    @EventListener(ContextStartedEvent.class)
    public void start() {
        System.out.print("Please write you name: ");
        String userName = inputService.getConsoleStrValue();

        Integer countRightAnswerConsole = questionService.getCountRightAnswerConsole();

        String resultTest = countRightAnswerConsole > propertyService.getCountRightAnswer() ? "passed" : "failed";

        System.out.printf("Dear %s, count right answer %s. Test %s.%n", userName, countRightAnswerConsole, resultTest);
    }

}
