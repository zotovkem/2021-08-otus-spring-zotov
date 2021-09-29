package ru.otus.spring.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Locale;

/**
 * @author Created by ZotovES on 19.09.2021
 * Проперти приложения
 */
@Getter
@Component
public class ApplicationProperties {
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

    private final Locale currentLocal;

    public ApplicationProperties(@Value("${app.csv-file-path.answer}") Resource answerDataFile,
            @Value("${app.csv-file-path.question}") Resource questionDataFile,
            @Value("${app.count-right-answer}") String countRightAnswer, @Value("${app.current-locale:en_EN}") Locale currentLocal) {
        this.answerDataFile = answerDataFile;
        this.questionDataFile = questionDataFile;
        this.countRightAnswer = Integer.parseInt(countRightAnswer);

        this.currentLocal = currentLocal;
    }
}
