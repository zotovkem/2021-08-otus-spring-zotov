package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.service.AnswerService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.08.2021
 */
@DisplayName("Тестирование сервиса вопросов")
@ExtendWith(MockitoExtension.class)
class QuestionServiceImplTest {
    @Mock private AnswerService answerService;
    @Mock private QuestionDao questionDao = mock(QuestionDao.class);
    @InjectMocks private QuestionServiceImpl questionService;

    @Test
    @DisplayName("Вывод на печать всех вопросов")
    void printConsoleAllQuestionsTest() {
        when(answerService.getConsoleAnswerByQuestionId(anyInt())).thenReturn(Optional.of(new Answer(1, 1, "test", true)));
        when(questionDao.findByAll()).thenReturn(List.of(new Question(1, "testQuestion", "test"),
                new Question(2, "testQuestion2", "test1")));
        Integer countRightAnswerConsole = questionService.getCountRightAnswerConsole();

        assertEquals(1, countRightAnswerConsole);

        verify(questionDao).findByAll();
        verify(answerService, times(2)).getConsoleAnswerByQuestionId(anyInt());
    }
}
