package ru.otus.spring.service;

import ru.otus.spring.model.Answer;

import java.util.Optional;

/**
 * @author Created by ZotovES on 30.08.2021
 * Сервис вариантов ответов на вопросы
 */
public interface AnswerService {
    /**
     * Вывести на печать варианты ответа по ид вопроса
     *
     * @param questionId ид вопроса
     */
    void printAnswersByQuestionId(Integer questionId);

    /**
     * Поиск варианта ответа по ид вопроса и номеру ответа
     *
     * @param questionId ид вопроса
     * @param number     номер варианта ответа
     * @return ответ
     */
    Optional<Answer> findByQuestionIdAndNumber(Integer questionId, Integer number);

    /**
     * Поиск варианта ответа по ид вопроса и номеру ответа
     *
     * @param questionId ид вопроса
     * @param number     номер варианта ответа
     * @return ответ
     */
    Optional<Answer> findByQuestionIdAndNumber(Integer questionId, String number);
}
