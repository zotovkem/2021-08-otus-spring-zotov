package ru.zotov.integration.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zotov.integration.domain.*;
import ru.zotov.integration.service.DeveloperService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 10.01.2022
 * Реализация сервиса разработчика
 */
@Slf4j
@Service
public class DeveloperServiceImpl implements DeveloperService {
    /**
     * Разработка задачи
     *
     * @param task задача
     * @return пулл реквест на задачу
     */
    @Override
    public PullRequest developTask(Task task) {
        log.info("[DeveloperFlow] Разработчик завершил задачу {}", task.getName());
        return new PullRequest("PR к задаче " + task.getName(), task);
    }

    /**
     * Фикс бага
     *
     * @param bug баг
     * @return пулл реквест на баг
     */
    @Override
    public PullRequest fixBug(Bug bug) {
        log.info("[DeveloperFlow] Разработчик фиксит баг {}", bug.getName());
        return new PullRequest("PR к багу " + bug.getName(), bug);
    }

    /**
     * Исправление замечания по PullRequest
     *
     * @param discussions Замечания
     * @return пулл реквест с исправлением
     */
    @Override
    public PullRequest editPullRequest(List<Discussion> discussions) {
        String listNameTasks = discussions.stream().map(Discussion::getName).collect(Collectors.joining(", "));
        PullRequest pullRequest = discussions.stream().map(Discussion::getPullRequest).distinct().findFirst().orElseThrow();
        log.info("[DeveloperFlow] Разработчик исправляет замечания по код ревью {}", pullRequest.getName());
        return pullRequest;
    }

    /**
     * Код ревью пулл реквеста
     *
     * @param pullRequest пулл реквест
     * @return замечания по пулл реквесту
     */
    @Override
    public List<Discussion> codeReview(PullRequest pullRequest) {
        log.info("[DeveloperFlow] Разработчик проводит код ревью {}", pullRequest.getName());
        return List.of(new Discussion(pullRequest.getName() + " Замечание разработчика1", pullRequest),
                new Discussion(pullRequest.getName() + " Замечание разработчика2", pullRequest));
    }

}
