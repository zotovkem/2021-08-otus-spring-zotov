package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.AnswerService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация сервисаа вариантов ответов на вопрос
 */
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private static final String DELEMITER_QUESTION_STRING =
            "=========================================================================";
    private final AnswerDao answerDao;

    /**
     * Вывод всех вариантов ответов по ид вопроса
     *
     * @param questionId ид вопроса
     */
    @Override
    public void printConsoleAnswerByQuestionId(@NonNull Integer questionId) {
        List<Answer> answers = answerDao.findByQuestionId(questionId);
        if (answers.isEmpty()) {
            System.out.println(" - Please input your answer:");
        }
        answers.forEach(answer -> System.out.printf(" - %s%n", answer.getAnswerText()));
    }

    /**
     * Получить ответ по ид введенного м консоли
     *
     * @param questionId ид вопроса
     */
    @Override
    public Optional<Answer> getConsoleAnswerByQuestionId(Integer questionId) {
        Scanner scanner = new Scanner(System.in);
        List<Answer> answers = answerDao.findByQuestionId(questionId);
        if (answers.isEmpty()) {
            System.out.print(" - Please input your answer: ");
            return Optional.of(new Answer(1, questionId, scanner.nextLine().trim().toLowerCase(), true));
        }
        AtomicInteger i = new AtomicInteger(1);
        answers.forEach(answer -> System.out.printf("%s %s%n", i.getAndIncrement(), answer.getAnswerText()));

        System.out.printf(" - Please input number answer 1-%s: ", i.get());
        int numberAnswer = scanner.nextInt();
        if (numberAnswer <= 0 || numberAnswer - 1 > answers.size()) {
            return Optional.empty();
        }
        System.out.println(DELEMITER_QUESTION_STRING);

        return Optional.ofNullable(answers.get(numberAnswer - 1));
    }
}
