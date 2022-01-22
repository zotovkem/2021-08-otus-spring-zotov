package ru.zotov.integration.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zotov.integration.config.DeveloperFlowGateway;
import ru.zotov.integration.domain.Epic;
import ru.zotov.integration.service.AnalystService;

import java.util.concurrent.ForkJoinPool;

/**
 * @author Created by ZotovES on 10.01.2022
 * Реализация сервис аналитика
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AnalystServiceImpl implements AnalystService {
    private final DeveloperFlowGateway gateway;
    private final ForkJoinPool pool = ForkJoinPool.commonPool();

    /**
     * Аналитик создает ТЗ на фичу
     * @param epicName имя эпика
     */
    @Override
    public void createEpic(String epicName) {
        log.info("[DeveloperFlow] Аналитик создал эпик {}", epicName);
        pool.execute( () -> {
            Epic completeFeature = gateway.process(new Epic(epicName));
            log.info("[DeveloperFlow] Аналитик получает готовую фичу {}", completeFeature.getName());
        });
    }
}
