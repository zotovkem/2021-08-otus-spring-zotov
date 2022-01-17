package ru.zotov.integration.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.zotov.integration.domain.Bug;
import ru.zotov.integration.domain.Epic;
import ru.zotov.integration.service.QaService;

import java.util.List;

/**
 * @author Created by ZotovES on 10.01.2022
 * Реализация сервиса тестирования
 */
@Slf4j
@Service
public class QaServiceImpl implements QaService {
    /**
     * Тестирование по ТЗ
     *
     * @param epic эпик
     * @return список багов
     */
    public List<Bug> testEpic(Epic epic) {
        log.info("[DeveloperFlow] QA тестирует эпик "+epic.getName());
        return List.of(new Bug("баг1",epic), new Bug("баг2",epic));
    }

    /**
     * Тестирование исправления по багу
     * @param bug баг
     * @return список багов
     */
    public Epic testBug(Bug bug){
        log.info("[DeveloperFlow] QA тестирует bug "+bug.getName());

        return bug.getEpic();
    }
}
