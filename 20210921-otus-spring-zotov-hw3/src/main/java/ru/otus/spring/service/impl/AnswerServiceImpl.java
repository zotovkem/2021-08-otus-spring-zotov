package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.InputService;
import ru.otus.spring.service.LocalizationService;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация сервиса вариантов ответов на вопрос
 */
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private static final String DELIMITER_QUESTION_STRING =
            "=========================================================================";
    private final AnswerDao answerDao;
    private final InputService inputService;
    private final LocalizationService localizationService;

    /**
     * Получить ответ по ид введенного м консоли
     *
     * @param questionId ид вопроса
     */
    @Override
    public Optional<Answer> getConsoleAnswerByQuestionId(Integer questionId) {
        List<Answer> answers = answerDao.findByQuestionId(questionId);
        if (answers.isEmpty()) {
            System.out.print(localizationService.getLocalizationTextByTag("tag.answer.input"));
            return Optional.of(new Answer(1, questionId, inputService.getConsoleStrValue().toLowerCase()));
        }
        AtomicInteger i = new AtomicInteger(1);
        answers.forEach(answer -> System.out.printf("%s %s%n", i.getAndIncrement(), answer.getAnswerText()));

        String countVersionAnswer = String.valueOf(i.get() - 1);
        System.out.print(localizationService.getLocalizationTextByTag("tag.number.answer.input", List.of(countVersionAnswer)));
        int numberAnswer = inputService.getConsoleIntValue();
        if (numberAnswer <= 0 || numberAnswer - 1 > answers.size()) {
            return Optional.empty();
        }
        System.out.println(DELIMITER_QUESTION_STRING);

        return Optional.ofNullable(answers.get(numberAnswer - 1));
    }
}
