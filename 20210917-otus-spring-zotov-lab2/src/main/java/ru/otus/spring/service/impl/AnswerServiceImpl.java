package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.AnswerService;

import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация сервисаа вариантов ответов на вопрос
 */
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
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
            System.out.println(" - Please input your answer:" );
        }
        answers.forEach(answer -> System.out.printf(" - %s%n", answer.getAnswerText()));
    }
}
