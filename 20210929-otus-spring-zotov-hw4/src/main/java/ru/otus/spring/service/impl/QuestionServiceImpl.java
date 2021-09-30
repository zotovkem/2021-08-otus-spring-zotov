package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.model.Question;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.LocalizationService;
import ru.otus.spring.service.QuestionService;

import java.util.function.Predicate;

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
     * Напечатать вопрос по ид
     *
     * @param id ид
     */
    @Override
    public void printQuestionById(Integer id) {
        questionDao.findById(id).ifPresentOrElse(question -> {
            System.out.println(question.getQuestionText());
            answerService.printAnswersByQuestionId(question.getId());
        }, () -> System.out.println(localizationService.getLocalizationTextByTag("taq.question.ended")));
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

    private Predicate<String> checkAnswerByNumberPredicate(Integer questionId, String answer) {
        return rightAnswerText -> rightAnswerText.equals(answer) ||
                answerService.findByQuestionIdAndNumber(questionId, answer).stream()
                        .map(Answer::getAnswerText)
                        .anyMatch(rightAnswerText::equals);
    }
}
