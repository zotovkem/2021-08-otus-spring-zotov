package ru.zotov.integration.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zotov.integration.domain.Discussion;
import ru.zotov.integration.domain.Epic;
import ru.zotov.integration.domain.PullRequest;
import ru.zotov.integration.domain.Task;

import java.util.List;

/**
 * @author Created by ZotovES on 10.01.2022
 * Тимлид сервис
 */
@Slf4j
@Service
public class TeamLeadService {
    public List<Task> splitEpic(Epic epic) {
      log.info("[DeveloperFlow] Teamlead разбивает эпик на задачи");
        return List.of(new Task(), new Task());
    }

    /**
     * Код ревью пулл реквеста
     *
     * @param pullRequest пулл реквест
     * @return замечания по пулл реквесту
     */
    public List<Discussion> codeReview(PullRequest pullRequest) {
        log.info("[DeveloperFlow] Teamlead проводит код ревью PR");
        return List.of(new Discussion(), new Discussion());
    }

    /**
     * Мердж пулл реквеста
     *
     * @param pullRequest пулл реквест
     * @return ТЗ на тестирование
     */
    public Epic mergePullRequest(PullRequest pullRequest) {
        log.info("[DeveloperFlow] Teamlead мерджит PR");
        return new Epic();
    }

    /**
     * Деплой фичи на продакшн
     *
     * @param epic эпик фичи
     */
    public void deploy(Epic epic) {
        log.info("[DeveloperFlow] Teamlead деплоит фичу");
    }
}
