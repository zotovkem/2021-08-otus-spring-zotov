package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.PropertyService;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Question;
import ru.otus.spring.service.DataLoaderService;

import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация репозитория вопросов
 */
@Service
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    private final DataLoaderService dataLoaderService;
    private final PropertyService propertyService;
    private List<Question> data;

    /**
     * Получить все вопросы
     *
     * @return список вопросов
     */
    @Override
    public List<Question> findByAll() {
        loadData();
        return data;
    }

    /**
     * Загрузить записи
     */
    private void loadData() {
        if (data == null) {
            data = dataLoaderService.loadObjectList(Question.class, propertyService.getQuestionDataFile());
        }
    }
}
