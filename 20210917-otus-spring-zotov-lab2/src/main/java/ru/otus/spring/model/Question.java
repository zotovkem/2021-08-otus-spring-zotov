package ru.otus.spring.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 30.08.2021
 * Модель вопроса
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Question {
    /**
     * Идентификатор
     */
    private Integer id;
    /**
     * Текст вопроса
     */
    private String questionText;
    /**
     * Правильный ответ
     */
    private String rightAnswer;
}
