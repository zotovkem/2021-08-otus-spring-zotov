package ru.otus.spring.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.dao.DataLoader;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация репозитория ответов
 */
@Service
@RequiredArgsConstructor
public class AnswerDaoImpl implements AnswerDao {
    private final DataLoader dataLoaderService;
    private final ApplicationProperties propertyService;

    /**
     * Получить варианты ответов по ид вопроса
     *
     * @param questionId ид вопроса
     * @return список ответов
     */
    @Override
    public List<Answer> findByQuestionId(@NonNull Integer questionId) {
        return  dataLoaderService.loadObjectList(Answer.class, propertyService.getAnswerDataFile()).stream()
                .filter(answer -> questionId.equals(answer.getQuestionId()))
                .collect(Collectors.toList());
    }
}
