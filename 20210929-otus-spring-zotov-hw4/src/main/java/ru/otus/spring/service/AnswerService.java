package ru.otus.spring.service;

import ru.otus.spring.model.Answer;

import java.util.Optional;

/**
 * @author Created by ZotovES on 30.08.2021
 * Сервис вариантов ответов на вопросы
 */
public interface AnswerService {
    /**
     * Получить текст вариантов ответов по ид вопроса
     *
     * @param questionId ид вопроса
     */
    String getAnswersTextByQuestionId(Integer questionId);

    /**
     * Поиск варианта ответа по ид вопроса и номеру ответа
     *
     * @param questionId ид вопроса
     * @param answer     ответ
     * @return ответ
     */
    Optional<Answer> findByQuestionIdAndNumber(Integer questionId, String answer);
}
