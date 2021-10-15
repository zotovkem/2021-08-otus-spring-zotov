package ru.otus.spring.service;

import ru.otus.spring.model.TestProgress;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 01.10.2021
 * Сервис управления прогрессом теста
 */
public interface TestProgressService {
    /**
     * Получить имя пользователя
     *
     * @return имя пользователя
     */
    Optional<String> getUserName();

    /**
     * Создать прогресс по тесту
     *
     * @param userName     имя пользователя
     * @param questionsIds список ид вопросов
     * @return прогресс по тесту
     */
    TestProgress create(String userName, List<Integer> questionsIds);

    /**
     * Отметить вопрос как решенный правильно
     *
     * @param questionId ид вопроса
     */
    void addRightQuestionId(Integer questionId);

    /**
     * Получить кол-во правильных ответов в тесте
     *
     * @return кол-во правильных ответов
     */
    Integer getCountRightQuestions();

    /**
     * Получить ид следующего вопроса
     *
     * @return ид вопроса
     */
    Integer getNextQuestionId();

    /**
     * Получить ид предыдущего вопроса
     *
     * @return ид вопроса
     */
    Integer getPrevQuestionId();

    /**
     * Получить ид текущего вопроса
     *
     * @return ид текущего вопроса
     */
    Optional<Integer> getCurrentQuestionId();

    /**
     * Удалить правильный ответ
     *
     * @param questionId ид вопроса
     */
    void deleteRightAnswer(Integer questionId);
}
