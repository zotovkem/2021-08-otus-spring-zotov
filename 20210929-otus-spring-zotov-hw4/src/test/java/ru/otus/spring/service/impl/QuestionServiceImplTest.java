package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Question;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.LocalizationService;

import java.util.Optional;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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
}
