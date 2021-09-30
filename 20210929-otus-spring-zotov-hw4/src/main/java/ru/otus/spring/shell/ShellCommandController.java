package ru.otus.spring.shell;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.TestService;

import java.util.Optional;

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
    @Getter
    @Setter
    private String userName;

    @ShellMethod(value = "Login command", key = {"l", "login"})
    public void login(@ShellOption(defaultValue = "Anonymous") String userName) {
        setUserName(userName);
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
    public void finishTest() {
        testService.finish(userName);
    }

    private Availability isLoginUser() {
        return Optional.ofNullable(getUserName())
                .map(u -> Availability.available())
                .orElseGet(() -> Availability.unavailable(localizationService.getLocalizationTextByTag("tag.login.error")));
    }

}
