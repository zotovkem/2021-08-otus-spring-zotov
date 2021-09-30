package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.LocalizationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
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
    @MockBean private LocalizationService localizationService;
    @Autowired private AnswerServiceImpl answerService;

    @Test
    @DisplayName("Вывод на печать вариантов ответов по ид вопроса")
    void printAnswersByQuestionIdTest() {
        when(localizationService.getLocalizationTextByTag(anyString(), anyList())).thenReturn("localizationTestText");
        List<Answer> answerList = new ArrayList<>(List.of(new Answer(1, 1, 1, "test"),
                new Answer(2, 2, 2, "test2"), new Answer(3, 1, 3, "test3")));
        when(answerDao.findByQuestionId(anyInt())).thenReturn(answerList);

        answerService.printAnswersByQuestionId(1);

        verify(answerDao).findByQuestionId(1);
        verify(localizationService).getLocalizationTextByTag(anyString(), anyList());
    }

    @Test
    @DisplayName("Поиск ответа по номеру вопроса и варианту ответа")
    void findByQuestionIdAndNumberTest() {
        Optional<Answer> answer = Optional.of(new Answer(1, 1, 1, "test"));
        when(answerDao.findByQuestionIdAndNumber(anyInt(), anyInt())).thenReturn(answer);

        Optional<Answer> result = answerService.findByQuestionIdAndNumber(1, 1);

        assertThat(result).isPresent().isEqualTo(answer);

        verify(answerDao).findByQuestionIdAndNumber(1, 1);
    }

    @Test
    @DisplayName("Поиск ответа по номеру вопроса и тексту ответа")
    void findByQuestionIdAndNumberStringTest() {
        Optional<Answer> answer = Optional.of(new Answer(1, 1, 1, "test"));
        when(answerDao.findByQuestionIdAndNumber(anyInt(), anyString())).thenReturn(answer);

        Optional<Answer> result = answerService.findByQuestionIdAndNumber(1, "if");

        assertThat(result).isPresent().isEqualTo(answer);

        verify(answerDao).findByQuestionIdAndNumber(1, "if");
    }
}
