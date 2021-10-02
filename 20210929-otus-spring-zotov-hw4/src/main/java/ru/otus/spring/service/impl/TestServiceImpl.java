package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.exception.AuthorizationException;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionService;
import ru.otus.spring.service.TestProgressService;
import ru.otus.spring.service.TestService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    private final TestProgressService testProgressService;

    /**
     * Старт теста
     */
    @Override
    public void start(String userName) {
        testProgressService.create(userName, questionService.getAllIds());
    }

    /**
     * Перейти на следующий вопрос
     */
    @Override
    public String getNextQuestionText() {
        return questionService.getQuestionTextById(testProgressService.getNextQuestionId());
    }

    /**
     * Получить текст предыдущего вопроса
     *
     * @return текст вопроса
     */
    @Override
    public String getPrevQuestionText() {
        return questionService.getQuestionTextById(testProgressService.getPrevQuestionId());
    }

    /**
     * Проверить ответ на вопрос
     *
     * @param answer ответ
     */
    @Override
    public void checkAnswer(String answer) {
        testProgressService.getCurrentQuestionId().ifPresent(qId -> {
            if (questionService.checkAnswer(qId, answer)) {
                testProgressService.addRightQuestionId(qId);
            } else {
                testProgressService.deleteRightAnswer(qId);
            }
        });
    }

    /**
     * Закончить тест
     */
    @Override
    public String finish() {
        Integer countRightQuestions = testProgressService.getCountRightQuestions();
        var resultTest = countRightQuestions > propertyService.getCountRightAnswer() ? "tag.test.passed" : "tag.test.failed";
        Optional<String> userName = testProgressService.getUserName();
        testProgressService.create(null, Collections.emptyList());

        return userName
                .map(username -> localizationService.getLocalizationTextByTag("tag.test.result",
                        List.of(username, String.valueOf(countRightQuestions),
                                localizationService.getLocalizationTextByTag(resultTest))))
                .orElseThrow(AuthorizationException::new);
    }
}
