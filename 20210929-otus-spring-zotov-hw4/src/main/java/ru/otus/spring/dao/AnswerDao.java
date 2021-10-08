package ru.otus.spring.dao;

import org.springframework.lang.NonNull;
import ru.otus.spring.model.Answer;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 30.08.2021
 * Репозиторий ответов на вопросы
 */
public interface AnswerDao {
    /**
     * Получить варианты ответов по ид вопроса
     *
     * @param questionId ид вопроса
     * @return список ответов
     */
    List<Answer> findByQuestionId(@NonNull Integer questionId);

    /**
     * Получить вариант ответа по ид вопроса и номеру варианта
     *
     * @param questionId ид вопроса
     * @param number     номер варианта ответа
     * @return список ответов
     */
    Optional<Answer> findByQuestionIdAndNumber(@NonNull Integer questionId, @NonNull String number);
}
