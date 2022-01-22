package ru.zotov.integration.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zotov.integration.domain.*;
import ru.zotov.integration.service.TeamLeadService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 10.01.2022
 * Реализация тимлид сервиса
 */
@Slf4j
@Service
public class TeamLeadServiceImpl implements TeamLeadService {
    /**
     * Тимлид разбивает эпик на задачи
     * @param epic эпик
     * @return список задач
     */
    @Override
    public List<Task> splitEpic(Epic epic) {
        log.info("[DeveloperFlow] Teamlead разбивает эпик " + epic.getName() + " на задачи ");
        return List.of(new Task("задача1", epic), new Task("задача2", epic));
    }

    /**
     * Код ревью пулл реквеста
     *
     * @param pullRequest пулл реквест
     * @return замечания по пулл реквесту
     */
    @Override
    public List<Discussion> codeReview(PullRequest pullRequest) {
        log.info("[DeveloperFlow] Teamlead проводит код ревью PR {}", pullRequest.getName());
        return List.of(new Discussion(pullRequest.getName() + " замечание лида1", pullRequest),
                new Discussion(pullRequest.getName() + " замечание лида2", pullRequest));
    }

    /**
     * Мердж пулл реквеста задачи
     *
     * @param pullRequest пулл реквест
     * @return задача
     */
    @Override
    public Task taskMergePullRequest(PullRequest pullRequest) {
        log.info("[DeveloperFlow] Teamlead мерджит PR {}", pullRequest.getName());
        return ((Task) pullRequest.getTask());
    }

    /**
     * Мердж пулл реквеста бага
     *
     * @param pullRequest пулл реквест
     * @return баг
     */
    @Override
    public Bug bugMergePullRequest(PullRequest pullRequest) {
        log.info("[DeveloperFlow] Teamlead мерджит PR {}", pullRequest.getName());
        return ((Bug) pullRequest.getTask());
    }

    /**
     * Передать эпик на тестирование
     *
     * @param tasks список задач
     * @return задача
     */
    @Override
    public Epic submitToTest(List<Task> tasks) {
        String listNameTasks = tasks.stream().map(Task::getName).collect(Collectors.joining(", "));
        Epic epic = tasks.stream().map(Task::getEpic).distinct().findFirst().orElseThrow();
        log.info("[DeveloperFlow] Teamlead задачи {} влиты, передаем эпик {} на тестирование", listNameTasks, epic.getName());
        return epic;
    }

    /**
     * Деплой фичи на продакшн
     *
     * @param epic эпик фичи
     */
    @Override
    public Epic deploy(Epic epic) {
        log.info("[DeveloperFlow] Teamlead деплоит фичу {}", epic.getName());
        return epic;
    }
}
