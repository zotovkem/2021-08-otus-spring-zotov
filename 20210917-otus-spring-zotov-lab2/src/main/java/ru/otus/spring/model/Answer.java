package ru.otus.spring.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 30.08.2021
 * Варианты ответа на вопрос
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Answer {
    /**
     * Идентификатор
     */
//    @JsonProperty("id")
    private Integer id;
    /**
     * Ид вопроса
     */
//    @JsonProperty("questionId")
    private Integer questionId;
    /**
     * Текст варианта ответа
     */
//    @JsonProperty("answerText")
    private String answerText;
}
