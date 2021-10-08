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
import ru.otus.spring.model.Question;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.09.2021
 */
@DisplayName("Тестирование репозитория вопросов")
@SpringBootTest(classes = QuestionDaoImpl.class)
class QuestionDaoImplTest {
    @MockBean private DataLoader dataLoaderService;
    @MockBean private ApplicationProperties propertyService;
    @Autowired private QuestionDaoImpl questionDao;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Получить все вопросы")
    void findByAllTest() {
        Question question = new Question(1, "testQuestion", "test");
        when(propertyService.getQuestionDataFile()).thenReturn(mock(Resource.class));
        when(dataLoaderService.loadObjectList(any(), any())).thenReturn(List.of(question));

        List<Question> result = questionDao.findByAll();

        assertThat(result).hasSize(1).containsOnly(question);

        verify(propertyService).getQuestionDataFile();
        verify(dataLoaderService).loadObjectList(any(), any());
    }

    @Test
    @DisplayName("Поиск вопроса по ид")
    void findByIdTest() {
        Question question = new Question(1, "testQuestion", "test");
        when(propertyService.getQuestionDataFile()).thenReturn(mock(Resource.class));
        when(dataLoaderService.loadObjectList(any(), any())).thenReturn(List.of(question));

        Optional<Question> result = questionDao.findById(1);

        assertThat(result).isPresent().hasValue(question);

        verify(propertyService).getQuestionDataFile();
        verify(dataLoaderService).loadObjectList(any(), any());
    }

    @Test
    @DisplayName("Поиск не существующего вопроса")
    void findByIdNotFoundQuestionTest() {
        Question question = new Question(1, "testQuestion", "test");
        when(propertyService.getQuestionDataFile()).thenReturn(mock(Resource.class));
        when(dataLoaderService.loadObjectList(any(), any())).thenReturn(List.of(question));

        Optional<Question> result = questionDao.findById(2);

        assertThat(result).isEmpty();

        verify(propertyService).getQuestionDataFile();
        verify(dataLoaderService).loadObjectList(any(), any());
    }
}
