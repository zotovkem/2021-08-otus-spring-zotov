package ru.otus.spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Question;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.QuestionService;

import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.08.2021
 */
@DisplayName("Тестирование сервиса вопросов")
class QuestionServiceImplTest {
    private final AnswerService answerService = mock(AnswerService.class);
    private final QuestionDao questionDao = mock(QuestionDao.class);
    private final QuestionService questionService = new QuestionServiceImpl(questionDao, answerService);

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Вывод на печать всех вопросов")
    void printConsoleAllQuestionsTest() {
        when(questionDao.findByAll()).thenReturn(List.of(new Question(1, "testQuestion"),
                new Question(2, "testQuestion2")));
        questionService.printConsoleAllQuestions();

        verify(questionDao).findByAll();
        verify(answerService, times(2)).printConsoleAnswerByQuestionId(anyInt());
    }
}
