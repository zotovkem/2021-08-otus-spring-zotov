package ru.otus.spring.service;

/**
 * @author Created by ZotovES on 22.09.2021
 * Сервис тестирования
 */
public interface TestService {
    /**
     * Старт теста
     *
     * @param userName имя пользователя
     */
    void start(String userName);

    /**
     * Получить текст следующего вопроса
     *
     * @return текст вопроса
     */
    String getNextQuestionText();

    /**
     * Получить текст предыдущего вопроса
     *
     * @return текст вопроса
     */
    String getPrevQuestionText();

    /**
     * Проверить ответ
     *
     * @param answer ответ
     */
    void checkAnswer(String answer);

    /**
     * Закончить тест
     */
    String finish();
}
