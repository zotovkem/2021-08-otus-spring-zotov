package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.TestService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 22.09.2021
 * Реализация сервиса тестирования
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionService questionService;
    private final ApplicationProperties propertyService;
    private final LocalizationService localizationService;

    private Set<Integer> rightQuestionIds = new HashSet<>();
    private int currentQuestion = 0;

    /**
     * Старт теста
     */
    @Override
    public void start(String userName) {
        clearResult();
        System.out.println(localizationService.getLocalizationTextByTag("tag.your.name", List.of(userName)));
    }

    /**
     * Перейти на следующий вопрос
     */
    @Override
    public void nextQuestion() {
        questionService.printQuestionById(++currentQuestion);
    }

    /**
     * Проверить ответ на вопрос
     *
     * @param answer ответ
     */
    @Override
    public void checkAnswer(String answer) {
        if (questionService.checkAnswer(currentQuestion, answer)) {
            rightQuestionIds.add(currentQuestion);
        }
    }

    /**
     * Закончить тест
     */
    @Override
    public void finish(String userName) {
        var resultTest = rightQuestionIds.size() > propertyService.getCountRightAnswer() ? "tag.test.passed" : "tag.test.failed";
        resultTest = localizationService.getLocalizationTextByTag(resultTest);
        String resultTestText = localizationService.getLocalizationTextByTag("tag.test.result",
                List.of(userName, String.valueOf(rightQuestionIds.size()), resultTest));
        System.out.println(resultTestText);
        clearResult();
    }

    /**
     * Очищаем результат тестирования
     */
    private void clearResult() {
        rightQuestionIds = new HashSet<>();
        currentQuestion = 0;
    }

}
