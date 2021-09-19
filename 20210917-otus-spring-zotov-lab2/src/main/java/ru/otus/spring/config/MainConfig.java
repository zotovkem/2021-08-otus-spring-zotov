package ru.otus.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.otus.spring.dao.AnswerDao;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.dao.impl.AnswerDaoImpl;
import ru.otus.spring.dao.impl.QuestionDaoImpl;

/**
 * @author Created by ZotovES on 17.09.2021
 * Конфигурация для создания бинов приложения
 */
@Configuration
public class MainConfig {
    /**
     * Репозиторий вопросов
     */
    @Bean
    public QuestionDao getQuestionDao() {
        return new QuestionDaoImpl("question-data.csv");
    }

    /**
     * Репозиторий ответов на вопросы
     */
    @Bean
    public AnswerDao getAnswerDao() {
        return new AnswerDaoImpl("answer-data.csv");
    }
}
