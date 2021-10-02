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
import ru.otus.spring.service.TestProgressService;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.09.2021
 */
@DisplayName("Тестирование сервиса управления тестированием")
@SpringBootTest(classes = TestServiceImpl.class)
class TestServiceImplTest {
    @MockBean private QuestionService questionService;
    @MockBean private ApplicationProperties propertyService;
    @MockBean private LocalizationService localizationService;
    @MockBean private TestProgressService testProgressService;
    @Autowired private TestServiceImpl testService;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Старт тестирования")
    void startTest() {
        List<Integer> listIds = List.of(1, 2);
        when(questionService.getAllIds()).thenReturn(listIds);

        String testUserName = "testUserName";
        testService.start(testUserName);

        verify(questionService).getAllIds();
        verify(testProgressService).create(testUserName, listIds);
    }

    @Test
    @DisplayName("Перейти к следующему вопросу")
    void nextQuestionTest() {
        when(questionService.getQuestionTextById(anyInt())).thenReturn("questionText");
        String result = testService.getNextQuestionText();

        assertThat(result).isNotEmpty();

        verify(questionService).getQuestionTextById(anyInt());
        verify(testProgressService).getNextQuestionId();
    }

    @Test
    @DisplayName("Перейти к предыдущему вопросу")
    void prevQuestionTest() {
        when(questionService.getQuestionTextById(anyInt())).thenReturn("questionText");
        String result = testService.getPrevQuestionText();

        assertThat(result).isNotEmpty();

        verify(questionService).getQuestionTextById(anyInt());
        verify(testProgressService).getPrevQuestionId();
    }

    @Test
    @DisplayName("Проверить ответ, правильный ответ")
    void checkRightAnswerTest() {
        when(testProgressService.getCurrentQuestionId()).thenReturn(Optional.of(1));
        when(questionService.checkAnswer(anyInt(), anyString())).thenReturn(true);
        testService.checkAnswer("test");

        verify(questionService).checkAnswer(anyInt(), anyString());
        verify(testProgressService).addRightQuestionId(anyInt());
        verify(testProgressService, never()).deleteRightAnswer(anyInt());
    }

    @Test
    @DisplayName("Проверить ответ, не правильный ответ")
    void checkIncorrectAnswerTest() {
        when(testProgressService.getCurrentQuestionId()).thenReturn(Optional.of(1));
        when(questionService.checkAnswer(anyInt(), anyString())).thenReturn(false);
        testService.checkAnswer("test");

        verify(questionService).checkAnswer(anyInt(), anyString());
        verify(testProgressService, never()).addRightQuestionId(anyInt());
        verify(testProgressService).deleteRightAnswer(anyInt());
    }

    @Test
    @DisplayName("Закончить тестирование")
    void finishTest() {
        when(propertyService.getCountRightAnswer()).thenReturn(3);
        when(localizationService.getLocalizationTextByTag(anyString(), anyList())).thenReturn("testLocalizationMessage");
        when(localizationService.getLocalizationTextByTag(anyString())).thenReturn("testLocalizationMessage");
        when(testProgressService.getUserName()).thenReturn(Optional.of("testUserName"));

        testService.finish();

        verify(propertyService).getCountRightAnswer();
        verify(localizationService).getLocalizationTextByTag(anyString());
        verify(localizationService).getLocalizationTextByTag(anyString(), anyList());
        verify(testProgressService).create(any(), anyList());
    }
}
