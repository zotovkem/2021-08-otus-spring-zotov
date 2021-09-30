package ru.otus.spring.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.Resource;
import ru.otus.spring.dao.DataLoader;
import ru.otus.spring.dao.impl.CsvDataLoaderImpl;
import ru.otus.spring.exception.CannotReadFileException;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 22.09.2021
 */
@DisplayName("Тестирование загрузчика данных из CSV")
@SpringBootTest(classes = CsvDataLoaderImpl.class)
class DataLoadServiceImplTest {
    @MockBean Resource resource;
    @Autowired DataLoader dataLoadService;

    @Test
    @DisplayName("Загрузка файла c вопросами")
    void loadQuestionListTest() throws IOException {
        String mockFile = "\"id\";\"questionText\";\"rightAnswer\"\n" +
                "1;Condition operator?;if\n" +
                "2;How to declare a class?;class MyClass {}";
        InputStream is = new ByteArrayInputStream(mockFile.getBytes());
        when(resource.getInputStream()).thenReturn(is);

        List<Question> list = dataLoadService.loadObjectList(Question.class, resource);

        assertThat(list.size()).isEqualTo(2);
        assertThat(list).anySatisfy(e -> {
            assertThat(e.getId()).isEqualTo(1);
            assertThat(e.getQuestionText()).isEqualTo("Condition operator?");
            assertThat(e.getRightAnswer()).isEqualTo("if");
        }).anySatisfy(e -> {
            assertThat(e.getId()).isEqualTo(2);
            assertThat(e.getQuestionText()).isEqualTo("How to declare a class?");
            assertThat(e.getRightAnswer()).isEqualTo("class MyClass {}");
        });
    }

    @Test
    @DisplayName("Загрузка файла c ответами")
    void loadAnswerListTest() throws IOException {
        String mockFile = "\"id\";\"questionId\";\"answerText\"\n" +
                "1;2;class MyClass {}\n" +
                "2;2;new class MyClass {}\n";
        InputStream is = new ByteArrayInputStream(mockFile.getBytes());
        when(resource.getInputStream()).thenReturn(is);

        List<Answer> list = dataLoadService.loadObjectList(Answer.class, resource);

        assertThat(list.size()).isEqualTo(2);
        assertThat(list).anySatisfy(e -> {
            assertThat(e.getId()).isEqualTo(1);
            assertThat(e.getQuestionId()).isEqualTo(2);
            assertThat(e.getAnswerText()).isEqualTo("class MyClass {}");
        }).anySatisfy(e -> {
            assertThat(e.getId()).isEqualTo(2);
            assertThat(e.getQuestionId()).isEqualTo(2);
            assertThat(e.getAnswerText()).isEqualTo("new class MyClass {}");
        });
    }

    @Test
    @DisplayName("Тестирование ошибки")
    void loadListErrorTest() {
        when(resource.getFilename()).thenReturn("testErrorFile");

        Throwable thrown =
                assertThrows(CannotReadFileException.class, () -> dataLoadService.loadObjectList(Answer.class, resource));

        assertThat(thrown.getMessage()).isEqualTo("Error read file testErrorFile");
    }
}
