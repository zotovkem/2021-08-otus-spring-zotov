package ru.otus.spring.shell;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.TestService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;

/**
 * @author Created by ZotovES on 30.09.2021
 */
@DisplayName("Тестирование интерфейса shell контроллера")
@SpringBootTest(classes = ShellCommandController.class, properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
        ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT_ENABLED + "=false"})
class ShellCommandControllerTest {
    @MockBean private TestService testService;
    @MockBean private LocalizationService localizationService;
    @Autowired private ShellCommandController shellCommandController;

    @BeforeEach
    void setUp() {
    }

    @Test
    @DisplayName("Логин пользователя")
    void loginTest() {
        String testUser = "testUser";
        shellCommandController.login(testUser);

        verify(testService).start(testUser);
    }

    @Test
    @DisplayName("Перейти к следующему вопросу")
    void nextQuestionTest() {
        shellCommandController.nextQuestion();

        verify(testService).nextQuestion();
    }

    @Test
    @DisplayName("Ответить на вопрос")
    void answeringQuestionTest() {
        String answer = "answer";
        shellCommandController.answeringQuestion(answer);

        verify(testService).checkAnswer(answer);
    }

    @Test
    @DisplayName("Закончить тест")
    void finishTest() {
        shellCommandController.setUserName("testUserName");
        shellCommandController.finishTest();

        verify(testService).finish(anyString());
    }
}
