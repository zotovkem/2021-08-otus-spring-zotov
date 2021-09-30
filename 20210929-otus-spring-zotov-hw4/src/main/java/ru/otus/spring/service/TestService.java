package ru.otus.spring.service;

import java.util.Optional;

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
     */
    void finish();

    /**
     * Получить имя пользователя
     *
     * @return имя пользователя
     */
    Optional<String> qetUserName();
}
