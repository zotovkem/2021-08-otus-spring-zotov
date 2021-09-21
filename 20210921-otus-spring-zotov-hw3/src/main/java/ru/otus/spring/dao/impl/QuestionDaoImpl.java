package ru.otus.spring.dao.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.config.PropertyService;
import ru.otus.spring.dao.CsvFileDao;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Question;

import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация репозитория вопросов
 */
@Service
public class QuestionDaoImpl extends CsvFileDao<Question> implements QuestionDao {
    public QuestionDaoImpl(PropertyService propertyService) {
        super(propertyService.getQuestionDataFile(), Question.class);
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
