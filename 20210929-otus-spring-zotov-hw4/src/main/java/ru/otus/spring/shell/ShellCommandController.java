package ru.otus.spring.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.TestProgressService;
import ru.otus.spring.service.TestService;

import java.util.List;

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
    private final TestProgressService testProgressService;

    @SuppressWarnings("UnusedReturnValue")
    @ShellMethod(value = "Login command", key = {"l", "login"})
    public String login(@ShellOption(defaultValue = "Anonymous") String userName) {
        testService.start(userName);

        return localizationService.getLocalizationTextByTag("tag.your.name", List.of(userName));
    }

    @SuppressWarnings("UnusedReturnValue")
    @ShellMethodAvailability(value = "isLoginUser")
    @ShellMethod(value = "Next question", key = {"n", "next", "next-question"})
    public String nextQuestion() {
        return testService.getNextQuestionText();

    }

    @SuppressWarnings("UnusedReturnValue")
    @ShellMethodAvailability(value = "isLoginUser")
    @ShellMethod(value = "Previous question", key = {"p", "prev", "previous-question"})
    public String prevQuestion() {
        return testService.getPrevQuestionText();

    }

    @ShellMethodAvailability(value = "isLoginUser")
    @ShellMethod(value = "Answer the question", key = {"a", "answer"})
    public void answeringQuestion(String answer) {
        testService.checkAnswer(answer);
    }

    @SuppressWarnings("UnusedReturnValue")
    @ShellMethodAvailability(value = "isLoginUser")
    @ShellMethod(value = "Finish test", key = {"f", "finish"})
    public String finishTest() {
        return testService.finish();
    }

    private Availability isLoginUser() {
        return testProgressService.getUserName()
                .map(u -> Availability.available())
                .orElseGet(() -> Availability.unavailable(localizationService.getLocalizationTextByTag("tag.login.error")));
    }

}
