package ru.otus.spring.service;

import org.springframework.lang.NonNull;
import ru.otus.spring.model.Answer;

import java.util.Optional;

/**
 * @author Created by ZotovES on 30.08.2021
 * Сервис вариантов ответов на вопросы
 */
public interface AnswerService {
    /**
     * Вывод всех вариантов ответов по ид вопроса
     *
     * @param questionId ид вопроса
     */
    void printConsoleAnswerByQuestionId(@NonNull Integer questionId);

    /**
     * Получить ответ по ид введенного м консоли
     *
     * @param questionId ид вопроса
     */
    Optional<Answer> getConsoleAnswerByQuestionId(@NonNull Integer questionId);
}
