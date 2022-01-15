package ru.zotov.integration.service;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zotov.integration.domain.Bug;
import ru.zotov.integration.domain.Discussion;
import ru.zotov.integration.domain.PullRequest;
import ru.zotov.integration.domain.Task;

import java.util.List;

/**
 * @author Created by ZotovES on 10.01.2022
 * Сервис разработчика
 */
@Slf4j
@Service
public class DeveloperService {
    /**
     * Разработка задачи
     *
     * @param task задача
     * @return пулл реквест на задачу
     */
    public PullRequest developTask(Task task) {
        log.info("[DeveloperFlow] Разработчик завершил задачу {}",task.getName());
        return new PullRequest("PR к задаче "+task.getName());
    }

    /**
     * Фикс бага
     *
     * @param bug баг
     * @return пулл реквест на баг
     */
    public PullRequest fixBug(Bug bug) {
        log.info("[DeveloperFlow] Разработчик фиксит баг {}", bug.getName());
        return new PullRequest("PR к багу "+ bug.getName());
    }

    /**
     * Исправление замечания по PullRequest
     *
     * @param discussion Замечание
     * @return пулл реквест с исправлением
     */
    public PullRequest editPullRequest(Discussion discussion) {
        log.info("[DeveloperFlow] Разработчик исправляет замечания по код ревью {}",discussion.getName());
        return new PullRequest("PR к замечанию "+ discussion.getName());
    }

    /**
     * Код ревью пулл реквеста
     *
     * @param pullRequest пулл реквест
     * @return замечания по пулл реквесту
     */
    public List<Discussion> codeReview(PullRequest pullRequest) {
        log.info("[DeveloperFlow] Разработчик проводит код ревью {}",pullRequest.getName());
        return List.of(new Discussion(pullRequest.getName()+ "Замечание разработчика1"), new Discussion(pullRequest.getName()+ "Замечание разработчика2"));
    }

}
