package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.model.TestProgress;
import ru.otus.spring.service.TestProgressService;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static java.util.Optional.ofNullable;

/**
 * @author Created by ZotovES on 01.10.2021
 * Реализация сервиса управления прогрессом
 */
@Service
@RequiredArgsConstructor
public class TestProgressServiceImpl implements TestProgressService {
    private TestProgress testProgress;
    private int currentQuestionIdx;

    /**
     * Получить имя пользователя
     *
     * @return имя пользователя
     */
    @Override
    public Optional<String> getUserName() {
        return ofNullable(testProgress).map(TestProgress::getUserName);
    }

    /**
     * Создать прогресс по тесту
     *
     * @param userName     имя пользователя
     * @param questionsIds список ид вопросов
     * @return прогресс по тесту
     */
    @Override
    public TestProgress create(String userName, List<Integer> questionsIds) {
        testProgress = new TestProgress(userName, new HashSet<>(), questionsIds);
        currentQuestionIdx = -1;
        return testProgress;
    }

    /**
     * Отметить вопрос как решенный правильно
     *
     * @param questionId ид вопроса
     */
    @Override
    public void addRightQuestionId(Integer questionId) {
        Set<Integer> rightQuestionIds = testProgress.getRightQuestionIds();
        rightQuestionIds.add(questionId);
    }

    @Override
    public Integer getCountRightQuestions() {
        return testProgress.getRightQuestionIds().size();
    }

    /**
     * Получить ид следующего вопроса
     *
     * @return ид вопроса
     */
    @Override
    public Integer getNextQuestionId() {
        List<Integer> questionIds = testProgress.getQuestionIds();
        if (++currentQuestionIdx >= questionIds.size()) {
            currentQuestionIdx = questionIds.size();
            return -1;
        }
        return questionIds.get(currentQuestionIdx);
    }

    /**
     * Получить ид предыдущего вопроса
     *
     * @return ид вопроса
     */
    @Override
    public Integer getPrevQuestionId() {
        if (--currentQuestionIdx < 0) {
            currentQuestionIdx = -1;
            return -1;
        }

        return testProgress.getQuestionIds().get(currentQuestionIdx);
    }

    @Override
    public Optional<Integer> getCurrentQuestionId() {
        List<Integer> questionIds = ofNullable(testProgress).map(TestProgress::getQuestionIds).orElse(List.of());
        if (questionIds.isEmpty() || currentQuestionIdx < 0 || currentQuestionIdx >= questionIds.size()) {
            return Optional.empty();
        }
        return Optional.of(questionIds.get(currentQuestionIdx));
    }

    /**
     * Удалить правильный ответ
     *
     * @param questionId ид вопроса
     */
    @Override
    public void deleteRightAnswer(Integer questionId) {
        Set<Integer> rightQuestionIds = testProgress.getRightQuestionIds();
        rightQuestionIds.remove(questionId);
    }
}
