package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.otus.spring.dao.QuestionDao;
import ru.otus.spring.model.Answer;
import ru.otus.spring.service.AnswerService;
import ru.otus.spring.service.QuestionService;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация сервиса вопросов
 */
@Service
@RequiredArgsConstructor
public class QuestionServiceImpl implements QuestionService {
    private final QuestionDao questionDao;
    private final AnswerService answerService;

    /**
     * Вывод на печать в консоль всех вопросов
     */
    @Override
    public Integer getCountRightAnswerConsole() {
        AtomicInteger countRightAnswer = new AtomicInteger(0);
        questionDao.findByAll()
                .forEach(question -> {
                    System.out.println(question.getQuestionText());
                    answerService.getConsoleAnswerByQuestionId(question.getId())
                            .map(Answer::getAnswerText)
                            .map(String::toLowerCase)
                            .filter(answerText -> answerText.equals(question.getRightAnswer().toLowerCase()))
                            .ifPresent(answer -> countRightAnswer.incrementAndGet());
                });

        return countRightAnswer.get();
    }
}
