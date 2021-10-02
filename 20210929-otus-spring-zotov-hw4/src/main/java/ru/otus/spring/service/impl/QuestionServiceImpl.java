package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionService;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static java.lang.String.format;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация сервиса вопросов
 */
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final AnswerService answerService;
    private final LocalizationService localizationService;

    /**
     * Получить текст вопроса по ид
     *
     * @param id ид
     */
    @Override
    public String getQuestionTextById(Integer id) {
        return questionDao.findById(id)
                .map(question -> format("%s%n", question.getQuestionText()) +
                        answerService.getAnswersTextByQuestionId(question.getId()))
                .orElseGet(() -> localizationService.getLocalizationTextByTag("taq.question.ended"));
    }

    /**
     * Проверить ответ на вопрос
     *
     * @param questionId ид вопроса
     * @param answer     ответ
     * @return результат проверки
     */
    @Override
    public boolean checkAnswer(Integer questionId, String answer) {
        return questionDao.findById(questionId)
                .map(Question::getRightAnswer)
                .filter(checkAnswerByNumberPredicate(questionId, answer))
                .isPresent();
    }

    /**
     * Получить список ид всех вопросов
     *
     * @return список ид
     */
    @Override
    public List<Integer> getAllIds() {
        return questionDao.findByAll().stream().map(Question::getId).collect(Collectors.toList());
    }

    private Predicate<String> checkAnswerByNumberPredicate(Integer questionId, String answer) {
        return rightAnswerText -> rightAnswerText.equals(answer) ||
                answerService.findByQuestionIdAndNumber(questionId, answer).stream()
                        .map(Answer::getAnswerText)
                        .anyMatch(rightAnswerText::equals);
    }
}
