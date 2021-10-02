package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 01.10.2021
 * Прогресс теста
 */
@Getter
@Builder
@AllArgsConstructor
public class TestProgress {
    /**
     * Имя пользователя
     */
    private final String userName;
    /**
     * Список id правильно проеденных вопросов
     */
    private final Set<Integer> rightQuestionIds;
    /**
     * Список ид всех вопросов теста
     */
    private final List<Integer> questionIds;
}
