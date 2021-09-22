package ru.otus.spring.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

/**
 * @author Created by ZotovES on 19.09.2021
 * Сервис для считывания пропертей
 */
@Getter
@Service
public class PropertyService {
    /**
     * Ресурс файла с ответами
     */
    private final Resource answerDataFile;
    /**
     * Ресурс файла с вопросами
     */
    private final Resource questionDataFile;
    /**
     * Кол-во правильных ответов для зачета теста
     */
    private final Integer countRightAnswer;

    public PropertyService(@Value("${app.csv-file-path.answer}") Resource answerDataFile,
            @Value("${app.csv-file-path.question}") Resource questionDataFile,
            @Value("${app.count-right-answer}") String countRightAnswer) {
        this.answerDataFile = answerDataFile;
        this.questionDataFile = questionDataFile;
        this.countRightAnswer = Integer.parseInt(countRightAnswer);

    }
}
