package ru.zotov.integration.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zotov.integration.config.DeveloperFlowGateway;
import ru.zotov.integration.domain.Discussion;
import ru.zotov.integration.domain.Epic;
import ru.zotov.integration.domain.PullRequest;

import java.util.List;

/**
 * @author Created by ZotovES on 10.01.2022
 * Сервис аналитика
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnalystService {
    private final DeveloperFlowGateway gateway;
    /**
     * Аналитик создает ТЗ на фичу
     *
     * @return эпик
     */
    public Epic createEpic() {
        String epicName= "Epic1";
        log.info("[DeveloperFlow] Аналитик создал эпик {}",epicName);
        return gateway.process(new Epic(epicName));
    }
}
