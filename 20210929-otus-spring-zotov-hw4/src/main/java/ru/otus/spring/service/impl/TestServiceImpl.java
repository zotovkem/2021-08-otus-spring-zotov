package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.service.InputService;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.TestService;

import java.util.List;

/**
 * @author Created by ZotovES on 22.09.2021
 * Реализация сервиса тестирования
 */
@Service
@RequiredArgsConstructor
public class TestServiceImpl implements TestService {
    private final QuestionService questionService;
    private final ApplicationProperties propertyService;
    private final InputService inputService;
    private final LocalizationService localizationService;

    /**
     * Старт теста
     */
    @Override
    public void start() {
        System.out.print(localizationService.getLocalizationTextByTag("tag.your.name"));
        String userName = inputService.getConsoleStrValue();

        Integer countRightAnswerConsole = questionService.getCountRightAnswerConsole();
        var resultTest = countRightAnswerConsole > propertyService.getCountRightAnswer() ? "tag.test.passed" : "tag.test.failed";
        resultTest = localizationService.getLocalizationTextByTag(resultTest);
        String resultTestText = localizationService.getLocalizationTextByTag("tag.test.result",
                List.of(userName, countRightAnswerConsole.toString(), resultTest));
        System.out.println(resultTestText);
    }

}
