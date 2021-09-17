package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.QuestionService;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация сервиса вопросов
 */
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final AnswerService answerService;

    /**
     * Вывод на печать в консоль всех вопросов
     */
    @Override
    public void printConsoleAllQuestions() {
        questionDao.findByAll()
                .forEach(question -> {
                    System.out.println(question.getQuestionText());
                    answerService.printConsoleAnswerByQuestionId(question.getId());
                });
    }
}
