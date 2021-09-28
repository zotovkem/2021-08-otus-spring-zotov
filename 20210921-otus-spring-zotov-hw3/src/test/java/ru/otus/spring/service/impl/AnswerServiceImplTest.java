package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.InputService;
import ru.otus.spring.service.LocalizationService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 30.08.2021
 */
@DisplayName("Тестирование сервиса вариантов ответов")
@SpringBootTest(classes = AnswerServiceImpl.class)
class AnswerServiceImplTest {
    @MockBean private AnswerDao answerDao;
    @MockBean private InputService inputService;
    @MockBean private LocalizationService localizationService;
    @Autowired private AnswerServiceImpl answerService;

    @Test
    @DisplayName("Вывод на печать списка вариантов ответов")
    void printConsoleAnswerByQuestionIdTest() {
        when(inputService.getConsoleIntValue()).thenReturn(1);
        when(localizationService.getLocalizationTextByTag(anyString(), anyList())).thenReturn("localizationTestText");
        when(answerDao.findByQuestionId(anyInt())).thenReturn(List.of(new Answer(1, 1, "test"),
                new Answer(2, 2, "test2"), new Answer(3, 1, "test3")));

        Optional<Answer> answer = answerService.getConsoleAnswerByQuestionId(1);

        assertTrue(answer.isPresent());
        assertEquals(1, answer.get().getId());

        verify(answerDao).findByQuestionId(1);
        verify(inputService).getConsoleIntValue();
        verify(localizationService).getLocalizationTextByTag(anyString(), anyList());
    }
}
