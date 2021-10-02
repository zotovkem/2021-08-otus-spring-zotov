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

import static java.lang.String.format;

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
     * Получить текст вариантов ответов по ид вопроса
     *
     * @param questionId ид вопроса
     */
    public String getAnswersTextByQuestionId(Integer questionId) {
        List<Answer> answers = answerDao.findByQuestionId(questionId);
        if (answers.isEmpty()) {
            return localizationService.getLocalizationTextByTag("tag.answer.input");
        }

        StringBuilder answersText = new StringBuilder();
        answers.sort(Comparator.comparingInt(Answer::getNumberAnswer));
        answers.forEach(answer -> answersText.append(format("%s %s%n", answer.getNumberAnswer(), answer.getAnswerText())));

        int countVersionAnswer = answers.stream().mapToInt(Answer::getNumberAnswer).max().orElse(1);
        answersText.append(localizationService.getLocalizationTextByTag("tag.number.answer.input",
                List.of(Integer.toString(countVersionAnswer))));

        return answersText.toString();
    }

    /**
     * Поиск варианта ответа по ид вопроса и номеру ответа
     *
     * @param questionId ид вопроса
     * @param answer     ответ
     * @return ответ
     */
    @Override
    public Optional<Answer> findByQuestionIdAndNumber(Integer questionId, String answer) {
        return answerDao.findByQuestionIdAndNumber(questionId, answer);
    }
}
