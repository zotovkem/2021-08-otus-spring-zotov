package ru.otus.spring.service;

/**
 * @author Created by ZotovES on 22.09.2021
 * Сервис тестирования
 */
public interface TestService {
    /**
     * Старт теста
     */
    void start(String userName);

    /**
     * Следующий вопрос
     */
    void nextQuestion();

    /**
     * Проверить ответ
     *
     * @param answer ответ
     */
    void checkAnswer(String answer);

    /**
     * Закончить тест
     *
     * @param userName имя пользователя
     */
    void finish(String userName);


}
