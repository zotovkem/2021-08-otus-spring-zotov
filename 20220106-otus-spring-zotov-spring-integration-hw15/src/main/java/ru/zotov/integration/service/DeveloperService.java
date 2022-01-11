package ru.zotov.integration.service;

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
    public PullRequest developTask(Task task) throws InterruptedException {
        Thread.sleep(200000);
        log.info("[DeveloperFlow] Разработчик завершил задачу");
        return new PullRequest();
    }

    /**
     * Фикс бага
     *
     * @param bug баг
     * @return пулл реквест на баг
     */
    public PullRequest fixBug(Bug bug) throws InterruptedException {
        Thread.sleep(50000);
        log.info("[DeveloperFlow] Разработчик фиксит баг");
        return new PullRequest();
    }

    /**
     * Исправление замечания по PullRequest
     *
     * @param discussion Замечание
     * @return пулл реквест с исправлением
     */
    public PullRequest editPullRequest(Discussion discussion) throws InterruptedException {
        Thread.sleep(30000);
        log.info("[DeveloperFlow] Разработчик исправляет замечания по код ревью");
        return new PullRequest();
    }

    /**
     * Код ревью пулл реквеста
     *
     * @param pullRequest пулл реквест
     * @return замечания по пулл реквесту
     */
    public List<Discussion> codeReview(PullRequest pullRequest) throws InterruptedException {
        Thread.sleep(100000);
        log.info("[DeveloperFlow] Разработчик проводит код ревью");
        return List.of(new Discussion(), new Discussion());
    }

}
