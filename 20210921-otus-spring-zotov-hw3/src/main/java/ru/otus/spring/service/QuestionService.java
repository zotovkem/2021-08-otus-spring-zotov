package ru.otus.spring.service;

/**
 * @author Created by ZotovES on 30.08.2021
 * Сервис вопросов
 */
public interface QuestionService {
    /**
     * Получить кол-во правильных ответов из теста
     */
    Integer getCountRightAnswerConsole();
}
