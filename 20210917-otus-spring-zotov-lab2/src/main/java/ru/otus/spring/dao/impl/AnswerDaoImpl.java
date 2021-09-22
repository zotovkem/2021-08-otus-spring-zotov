package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.PropertyService;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.DataLoaderService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация репозитория ответов
 */
@Service
@RequiredArgsConstructor
public class AnswerDaoImpl implements AnswerDao {
    private final DataLoaderService dataLoaderService;
    private final PropertyService propertyService;
    private List<Answer> data;

    /**
     * Получить варианты ответов по ид вопроса
     *
     * @param questionId ид вопроса
     * @return список ответов
     */
    @Override
    public List<Answer> findByQuestionId(@NonNull Integer questionId) {
        loadData();
        return data.stream()
                .filter(answer -> questionId.equals(answer.getQuestionId()))
                .collect(Collectors.toList());
    }

    /**
     * Загрузить записи
     */
    private void loadData() {
        if (data == null) {
            data = dataLoaderService.loadObjectList(Answer.class, propertyService.getAnswerDataFile());
        }
    }
}
