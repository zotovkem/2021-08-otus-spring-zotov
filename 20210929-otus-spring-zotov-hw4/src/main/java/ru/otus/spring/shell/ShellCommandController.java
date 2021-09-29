package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.TestService;

/**
 * @author Created by ZotovES on 29.09.2021
 * Контроллер shell команд
 */
@SuppressWarnings("unused")
@ShellComponent
@RequiredArgsConstructor
public class ShellCommandController {
    private final TestService testService;
    private final LocalizationService localizationService;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "Anonymous") String userName) {
        testService.start(userName);
    }

    @ShellMethodAvailability(value = "isLoginUser")
    @ShellMethod(value = "Next question", key = {"n", "next", "next-question"})
    public void nextQuestion() {
        testService.nextQuestion();
    }

    @ShellMethodAvailability(value = "isLoginUser")
    @ShellMethod(value = "Answer the question", key = {"a", "answer"})
    public void answeringQuestion(String answer) {
        testService.checkAnswer(answer);
    }

    @ShellMethodAvailability(value = "isLoginUser")
    @ShellMethod(value = "Finish test", key = {"f", "finish"})
    private void finishTest() {
        testService.finish();
    }

    private Availability isLoginUser() {
        return testService.qetUserName()
                .map(u -> Availability.available())
                .orElseGet(() -> Availability.unavailable(localizationService.getLocalizationTextByTag("tag.login.error")));
    }

}
