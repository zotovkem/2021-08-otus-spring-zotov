package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 30.08.2021
 * Варианты ответа на вопрос
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    /**
     * Идентификатор
     */
    private Integer id;
    /**
     * Ид вопроса
     */
    private Integer questionId;
    /**
     * Текст варианта ответа
     */
    private String answerText;
    /**
     * Признак правильного ответа
     */
    private Boolean isRight;
}
