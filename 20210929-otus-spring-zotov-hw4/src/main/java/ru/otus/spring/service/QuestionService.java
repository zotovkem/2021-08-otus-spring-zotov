package ru.otus.spring.service;

import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Сервис вопросов
 */
public interface QuestionService {
    /**
     * Получить текст вопроса по ид
     *
     * @param id ид
     */
    String getQuestionTextById(Integer id);

    /**
     * Проверить ответ на вопрос
     *
     * @param questionId ид вопроса
     * @param answer     ответ
     * @return результат проверки
     */
    boolean checkAnswer(Integer questionId, String answer);

    /**
     * Получить все идентификаторы вопросов
     *
     * @return список ид
     */
    List<Integer> getAllIds();
}
