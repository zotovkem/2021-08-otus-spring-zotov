package ru.otus.spring.service;

/**
 * @author Created by ZotovES on 30.08.2021
 * Сервис вопросов
 */
public interface QuestionService {
    /**
     * Напечатать вопрос по ид
     *
     * @param id ид
     */
    void printQuestionById(Integer id);

    /**
     * Проверить ответ на вопрос
     *
     * @param questionId ид вопроса
     * @param answer     ответ
     * @return результат проверки
     */
    boolean checkAnswer(Integer questionId, String answer);
}
