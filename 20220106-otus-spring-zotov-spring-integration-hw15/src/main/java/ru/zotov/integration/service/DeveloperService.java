package ru.zotov.integration.service;

import ru.zotov.integration.domain.Bug;
import ru.zotov.integration.domain.Discussion;
import ru.zotov.integration.domain.PullRequest;
import ru.zotov.integration.domain.Task;

import java.util.List;

/**
 * @author Created by ZotovES on 17.01.2022
 * Сервис разаработчика
 */
public interface DeveloperService {
    /**
     * Разработка задачи
     *
     * @param task задача
     * @return пулл реквест на задачу
     */
    PullRequest developTask(Task task);

    /**
     * Фикс бага
     *
     * @param bug баг
     * @return пулл реквест на баг
     */
    PullRequest fixBug(Bug bug);

    /**
     * Исправление замечания по PullRequest
     *
     * @param discussions Замечания
     * @return пулл реквест с исправлением
     */
    PullRequest editPullRequest(List<Discussion> discussions);

    /**
     * Код ревью пулл реквеста
     *
     * @param pullRequest пулл реквест
     * @return замечания по пулл реквесту
     */
    List<Discussion> codeReview(PullRequest pullRequest);
}
