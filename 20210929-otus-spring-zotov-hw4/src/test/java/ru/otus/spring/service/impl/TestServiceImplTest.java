package ru.otus.spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionService;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 30.09.2021
 */
@DisplayName("Тестирование сервиса управления тестированием")
@SpringBootTest(classes = TestServiceImpl.class)
class TestServiceImplTest {
    @MockBean private QuestionService questionService;
    @MockBean private ApplicationProperties propertyService;
    @MockBean private LocalizationService localizationService;
    @Autowired private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Старт тестирования")
    void startTest() {
        String testUser = "testUser";
        when(localizationService.getLocalizationTextByTag(anyString(), anyList())).thenReturn("testLocalizationMessage");

        testService.start(testUser);

        verify(localizationService).getLocalizationTextByTag(anyString(), anyList());
    }

    @Test
    @DisplayName("Перейти к следующему вопросу")
    void nextQuestionTest() {
        testService.nextQuestion();

        verify(questionService).printQuestionById(anyInt());
    }

    @Test
    @DisplayName("Проверить ответ")
    void checkAnswerTest() {
        testService.checkAnswer("test");

        verify(questionService).checkAnswer(anyInt(), anyString());
    }

    @Test
    @DisplayName("Закончить тестирование")
    void finishTest() {
        when(propertyService.getCountRightAnswer()).thenReturn(3);
        when(localizationService.getLocalizationTextByTag(anyString())).thenReturn("testLocalizationMessage");

        testService.finish("testUser");

        verify(propertyService).getCountRightAnswer();
        verify(localizationService).getLocalizationTextByTag(anyString());
        verify(localizationService).getLocalizationTextByTag(anyString(), anyList());
    }
}
