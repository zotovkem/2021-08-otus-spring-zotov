package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.InputService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 30.08.2021
 */
@DisplayName("Тестирование сервиса вариантов ответов")
@ExtendWith(MockitoExtension.class)
class AnswerServiceImplTest {
    @Mock private AnswerDao answerDao;
    @Mock private InputService inputService;
    @InjectMocks private AnswerServiceImpl answerService;

    @Test
    @DisplayName("Вывод на печать списка вариантов ответов")
    void printConsoleAnswerByQuestionIdTest() {
        when(inputService.getConsoleIntValue()).thenReturn(1);
        when(answerDao.findByQuestionId(anyInt())).thenReturn(List.of(new Answer(1, 1, "test", false),
                new Answer(2, 2, "test2", false), new Answer(3, 1, "test3", false)));

        Optional<Answer> answer = answerService.getConsoleAnswerByQuestionId(1);

        assertTrue(answer.isPresent());
        assertEquals(1, answer.get().getId());

        verify(answerDao).findByQuestionId(1);
        verify(inputService).getConsoleIntValue();
    }
}
