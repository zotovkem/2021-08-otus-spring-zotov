package ru.otus.spring.dao.impl;

import org.springframework.lang.NonNull;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.dao.CsvFileDao;
import ru.otus.spring.model.Answer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация репозитория ответов
 */
public class AnswerDaoImpl extends CsvFileDao<Answer> implements AnswerDao {
    public AnswerDaoImpl(String pathCsvFile) {
        super(pathCsvFile, Answer.class);
    }

    /**
     * Получить варианты ответов по ид вопроса
     *
     * @param questionId ид вопроса
     * @return список ответов
     */
    @Override
    public List<Answer> findByQuestionId(@NonNull Integer questionId) {
        return data.stream()
                .filter(answer -> questionId.equals(answer.getQuestionId()))
                .collect(Collectors.toList());
    }
}
