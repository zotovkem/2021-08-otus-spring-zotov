package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.dao.DataLoader;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Question;

import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация репозитория вопросов
 */
@Service
@RequiredArgsConstructor
public class QuestionDaoImpl implements QuestionDao {
    private final DataLoader dataLoaderService;
    private final ApplicationProperties propertyService;

    /**
     * Получить все вопросы
     *
     * @return список вопросов
     */
    @Override
    public List<Question> findByAll() {
        return dataLoaderService.loadObjectList(Question.class, propertyService.getQuestionDataFile());
    }
}
