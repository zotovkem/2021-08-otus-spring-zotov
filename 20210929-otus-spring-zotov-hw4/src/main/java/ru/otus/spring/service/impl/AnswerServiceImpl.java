package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.LocalizationService;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация сервиса вариантов ответов на вопрос
 */
@Service
@RequiredArgsConstructor
public class AnswerServiceImpl implements AnswerService {
    private final AnswerDao answerDao;
    private final LocalizationService localizationService;

    /**
     * Вывести на печать варианты ответа по ид вопроса
     *
     * @param questionId ид вопроса
     */
    public void printAnswersByQuestionId(Integer questionId) {
        List<Answer> answers = answerDao.findByQuestionId(questionId);
        if (answers.isEmpty()) {
            System.out.println(localizationService.getLocalizationTextByTag("tag.answer.input"));
            return;
        }
        answers.sort(Comparator.comparingInt(Answer::getNumberAnswer));
        answers.forEach(answer -> System.out.printf("%s %s%n", answer.getNumberAnswer(), answer.getAnswerText()));

        int countVersionAnswer = answers.stream().mapToInt(Answer::getNumberAnswer).max().orElse(1);
        System.out.println(localizationService.getLocalizationTextByTag("tag.number.answer.input",
                List.of(Integer.toString(countVersionAnswer))));
    }

    /**
     * Поиск варианта ответа по ид вопроса и номеру ответа
     *
     * @param questionId ид вопроса
     * @param number     номер варианта ответа
     * @return ответ
     */
    @Override
    public Optional<Answer> findByQuestionIdAndNumber(Integer questionId, Integer number) {
        return answerDao.findByQuestionIdAndNumber(questionId, number);
    }

    /**
     * Поиск варианта ответа по ид вопроса и номеру ответа
     *
     * @param questionId ид вопроса
     * @param number     номер варианта ответа
     * @return ответ
     */
    @Override
    public Optional<Answer> findByQuestionIdAndNumber(Integer questionId, String number) {
        return answerDao.findByQuestionIdAndNumber(questionId, number);
    }
}
