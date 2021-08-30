package ru.otus.spring.dao.impl;

import ru.otus.spring.dao.CsvFileDao;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Question;

import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация репозитория вопросов
 */
public class QuestionDaoImpl extends CsvFileDao<Question> implements QuestionDao {
    public QuestionDaoImpl(String pathCsvFile) {
        super(pathCsvFile, Question.class);
    }

    /**
     * Получить все вопросы
     *
     * @return список вопросов
     */
    @Override
    public List<Question> findByAll() {
        return data;
    }
}
