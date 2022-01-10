package ru.zotov.integration.service;

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

@Service
public class DeveloperService {
    /**
     * Разработка задачи
     * @param task задача
     * @return пулл реквест на задачу
     */
    PullRequest developTask(Task task){
        return new PullRequest();
    }

    /**
     * Фикс бага
     * @param bug баг
     * @return пулл реквест на баг
     */
    PullRequest fixBug(Bug bug){
        return new PullRequest();
    }

    /**
     * Исправление замечания по PullRequest
     * @param discussion Замечание
     * @return пулл реквест с исправлением
     */
    PullRequest editPullRequest(Discussion discussion){
        return new PullRequest();
    }

    /**
     * Код ревью пулл реквеста
     * @param pullRequest пулл реквест
     * @return замечания по пулл реквесту
     */
    List<Discussion> codeReview(PullRequest pullRequest){
        return List.of(new Discussion(), new Discussion());
    }

}
