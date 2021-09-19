package ru.otus.spring.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author Created by ZotovES on 19.09.2021
 * Сервис для считывания пропертей
 */
@Getter
@Service
public class PropertyService {
    /**
     * Имя csv файла с ответами
     */
    private final String answerDataFile;
    /**
     * Имя csv файла с вопросами
     */
    private final String questionDataFile;
    /**
     * Кол-во правильных ответов для зачета теста
     */
    private final Integer countRightAnswer;

    public PropertyService(@Value("${app.csv-file-path.answer}") String answerDataFile,
            @Value("${app.csv-file-path.question}") String questionDataFile,
            @Value("${app.count-right-answer}") String countRightAnswer) {
        this.answerDataFile = answerDataFile;
        this.questionDataFile = questionDataFile;
        this.countRightAnswer = Integer.parseInt(countRightAnswer);
    }
}
