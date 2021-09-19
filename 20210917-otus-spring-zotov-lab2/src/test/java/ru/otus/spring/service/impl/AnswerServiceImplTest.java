package ru.otus.spring.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.InputService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.08.2021
 */
@DisplayName("Тестирование сервиса вариантов ответов")
class AnswerServiceImplTest {
    private final AnswerDao answerDao = mock(AnswerDao.class);
    private final InputService inputService = mock(InputService.class);
    private final AnswerService answerService = new AnswerServiceImpl(answerDao, inputService);

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Вывод на печать списка вариантов ответов")
    void printConsoleAnswerByQuestionIdTest() {
        when(inputService.getConsoleIntValue()).thenReturn(1);
        when(inputService.getConsoleStrValue()).thenReturn("test");
        when(answerDao.findByQuestionId(anyInt())).thenReturn(List.of(new Answer(1, 1, "test", false),
                new Answer(2, 2, "test2", false), new Answer(3, 1, "test3", false)));

        Optional<Answer> answer = answerService.getConsoleAnswerByQuestionId(1);

        assertTrue(answer.isPresent());
        assertEquals(1, answer.get().getId());

        verify(answerDao).findByQuestionId(anyInt());
    }
}
