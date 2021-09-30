package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.LocalizationService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.08.2021
 */
@DisplayName("Тестирование сервиса вопросов")
@SpringBootTest(classes = QuestionServiceImpl.class)
class QuestionServiceImplTest {
    @MockBean private AnswerService answerService;
    @MockBean private QuestionDao questionDao;
    @MockBean private LocalizationService localizationService;
    @Autowired private QuestionServiceImpl questionService;

    @Test
    @DisplayName("Вывод на печать вопроса по id")
    void printQuestionByIdTest() {
        when(questionDao.findById(anyInt())).thenReturn(Optional.of(new Question(1, "testQuestion", "test")));

        questionService.printQuestionById(1);

        verify(questionDao).findById(anyInt());
        verify(answerService).printAnswersByQuestionId(anyInt());
    }

    @Test
    @DisplayName("Печать сообщения об окончании теста")
    void printEndedTest() {
        when(questionDao.findById(anyInt())).thenReturn(Optional.empty());

        questionService.printQuestionById(1);

        verify(questionDao).findById(anyInt());
        verify(localizationService).getLocalizationTextByTag(anyString());
        verify(answerService, never()).printAnswersByQuestionId(anyInt());
    }

    @Test
    @DisplayName("Успешная проверка ответа по номеру варианта")
    void checkAnswerByNumberTest() {
        when(questionDao.findById(anyInt())).thenReturn(Optional.of(new Question(1, "testQuestion", "test")));
        when(answerService.findByQuestionIdAndNumber(anyInt(), anyString())).thenReturn(Optional.of(new Answer(1, 1, 1, "test")));

        boolean result = questionService.checkAnswer(1, "1");

        assertTrue(result);

        verify(questionDao).findById(anyInt());
        verify(answerService).findByQuestionIdAndNumber(anyInt(), anyString());
    }

    @Test
    @DisplayName("Не успешная проверка ответа по номеру варианта")
    void notCheckAnswerByNumberTest() {
        when(questionDao.findById(anyInt())).thenReturn(Optional.of(new Question(1, "testQuestion", "test")));
        when(answerService.findByQuestionIdAndNumber(anyInt(), anyString())).thenReturn(Optional.empty());

        boolean result = questionService.checkAnswer(1, "1");

        assertFalse(result);

        verify(questionDao).findById(anyInt());
        verify(answerService).findByQuestionIdAndNumber(anyInt(), anyString());
    }

    @Test
    @DisplayName("Успешная проверка ответа по тексту ответа")
    void checkAnswerByTextTest() {
        when(questionDao.findById(anyInt())).thenReturn(Optional.of(new Question(1, "testQuestion", "test")));
        when(answerService.findByQuestionIdAndNumber(anyInt(), anyString())).thenReturn(Optional.of(new Answer(1, 1, 1, "test")));

        boolean result = questionService.checkAnswer(1, "test");

        assertTrue(result);

        verify(questionDao).findById(anyInt());
        verify(answerService, never()).findByQuestionIdAndNumber(anyInt(), anyString());
    }
}
