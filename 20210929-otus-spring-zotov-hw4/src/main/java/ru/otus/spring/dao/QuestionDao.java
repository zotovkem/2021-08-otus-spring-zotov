package ru.otus.spring.dao;

import ru.otus.spring.model.Question;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 30.08.2021
 * Репозиторий вопросов
 */
public interface QuestionDao {
    /**
     * Получить все вопросы
     *
     * @return список вопросов
     */
    List<Question> findByAll();

    /**
     * Получить вопрос по ид
     *
     * @param id ид
     * @return вопрос
     */
    Optional<Question> findById(Integer id);
}
