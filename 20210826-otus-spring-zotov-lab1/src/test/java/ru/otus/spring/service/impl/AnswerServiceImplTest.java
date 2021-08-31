package ru.otus.spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.AnswerService;

import java.util.List;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.08.2021
 */
@DisplayName("Тестирование сервиса вариантов ответов")
class AnswerServiceImplTest {
    private final AnswerDao answerDao = mock(AnswerDao.class);
    private final AnswerService answerService = new AnswerServiceImpl(answerDao);

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Вывод на печать списка вариантов ответов")
    void printConsoleAnswerByQuestionIdTest() {
        when(answerDao.findByQuestionId(anyInt())).thenReturn(List.of(new Answer(1,1,"test"),
                new Answer(2,2,"test2"),new Answer(3,1,"test3")));

        answerService.printConsoleAnswerByQuestionId(1);

        verify(answerDao).findByQuestionId(anyInt());
    }
}
