package ru.otus.spring.dao;

import org.springframework.lang.NonNull;
import ru.otus.spring.model.Answer;

import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Репозиторий ответов на вопросы
 */
public interface AnswerDao {
    /**
     * Получить варианты ответов по ид вопроса
     * @param questionId ид вопроса
     * @return список ответов
     */
    List<Answer> findByQuestionId(@NonNull Integer questionId);
}
