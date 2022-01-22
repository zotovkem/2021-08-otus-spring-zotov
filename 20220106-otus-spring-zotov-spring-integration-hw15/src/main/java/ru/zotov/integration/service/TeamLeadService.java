package ru.zotov.integration.service;

import ru.zotov.integration.domain.*;

import java.util.List;

/**
 * @author Created by ZotovES on 10.01.2022
 * Тимлид сервис
 */
public interface TeamLeadService {
    List<Task> splitEpic(Epic epic);

    /**
     * Код ревью пулл реквеста
     *
     * @param pullRequest пулл реквест
     * @return замечания по пулл реквесту
     */
    List<Discussion> codeReview(PullRequest pullRequest);

    /**
     * Мердж пулл реквеста задачи
     *
     * @param pullRequest пулл реквест
     * @return задача
     */
    Task taskMergePullRequest(PullRequest pullRequest);

    /**
     * Мердж пулл реквеста бага
     *
     * @param pullRequest пулл реквест
     * @return баг
     */
    Bug bugMergePullRequest(PullRequest pullRequest);

    /**
     * Передать эпик на тестирование
     *
     * @param tasks список задач
     * @return задача
     */
    Epic submitToTest(List<Task> tasks);

    /**
     * Деплой фичи на продакшн
     *
     * @param epic эпик фичи
     */
    Epic deploy(Epic epic);
}

