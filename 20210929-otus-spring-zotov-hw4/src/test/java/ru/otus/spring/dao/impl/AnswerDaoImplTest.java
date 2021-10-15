package ru.otus.spring.dao.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.dao.DataLoader;
import ru.otus.spring.model.Answer;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.09.2021
 */
@DisplayName("Тестирование репозитория ответов")
@SpringBootTest(classes = AnswerDaoImpl.class)
class AnswerDaoImplTest {
    @MockBean private DataLoader dataLoaderService;
    @MockBean private ApplicationProperties propertyService;
    @Autowired private AnswerDaoImpl answerDao;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Поиск ответа по ид вопроса")
    void findByQuestionIdTest() {
        Answer answer = new Answer(1, 1, 1, "test");
        when(propertyService.getQuestionDataFile()).thenReturn(mock(Resource.class));
        when(dataLoaderService.loadObjectList(any(), any())).thenReturn(List.of(answer));

        List<Answer> result = answerDao.findByQuestionId(1);

        assertThat(result).hasSize(1).containsOnly(answer);

        verify(propertyService).getAnswerDataFile();
        verify(dataLoaderService).loadObjectList(any(), any());
    }

    @Test
    @DisplayName("Поиск не существующего ответа по ид вопроса")
    void findByQuestionIdNotFoundTest() {
        Answer answer = new Answer(1, 1, 1, "test");
        when(propertyService.getQuestionDataFile()).thenReturn(mock(Resource.class));
        when(dataLoaderService.loadObjectList(any(), any())).thenReturn(List.of(answer));

        List<Answer> result = answerDao.findByQuestionId(2);

        assertThat(result).isEmpty();

        verify(propertyService).getAnswerDataFile();
        verify(dataLoaderService).loadObjectList(any(), any());
    }

    @Test
    @DisplayName("Поиск ответа по  ид вопроса и номеру варианта")
    void findByQuestionIdAndNumberTest() {
        Answer answer = new Answer(1, 1, 1, "test");
        when(propertyService.getQuestionDataFile()).thenReturn(mock(Resource.class));
        when(dataLoaderService.loadObjectList(any(), any())).thenReturn(List.of(answer));

        Optional<Answer> result = answerDao.findByQuestionIdAndNumber(1, "1");

        assertThat(result).isPresent().hasValue(answer);

        verify(propertyService).getAnswerDataFile();
        verify(dataLoaderService).loadObjectList(any(), any());
    }

    @Test
    @DisplayName("Поиск не существующего ответа по ид вопроса и номеру варианта")
    void findByQuestionIdAndNumberNotFoundTest() {
        Answer answer = new Answer(1, 1, 1, "test");
        when(propertyService.getQuestionDataFile()).thenReturn(mock(Resource.class));
        when(dataLoaderService.loadObjectList(any(), any())).thenReturn(List.of(answer));

        Optional<Answer> result = answerDao.findByQuestionIdAndNumber(2, "2");

        assertThat(result).isEmpty();

        verify(propertyService).getAnswerDataFile();
        verify(dataLoaderService).loadObjectList(any(), any());
    }
}
