package ru.otus.spring.model;

import lombok.Getter;

/**
 * @author Created by ZotovES on 30.08.2021
 * Модель вопроса
 */
@Getter
public class Question {
    /**
     * Идентификатор
     */
    private final Integer id;

    /**
     * Текст вопроса
     */
    private final String questionText;

    /**
     * Правильный ответ
     */
    private final String rightAnswer;

    @SuppressWarnings("unused")
    public Question() {
        this.id = null;
        this.questionText = null;
        this.rightAnswer = null;
    }

    public Question(Integer id, String questionText, String rightAnswer) {
        this.id = id;
        this.questionText = questionText;
        this.rightAnswer = rightAnswer;
    }
}
