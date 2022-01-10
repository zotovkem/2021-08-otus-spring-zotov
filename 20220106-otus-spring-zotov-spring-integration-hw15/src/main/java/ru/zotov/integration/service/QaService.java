package ru.zotov.integration.service;

import org.springframework.stereotype.Service;
import ru.zotov.integration.domain.Bug;
import ru.zotov.integration.domain.Epic;

import java.util.List;

/**
 * @author Created by ZotovES on 10.01.2022
 * Сервис тестирования
 */
@Service
public class QaService {
    /**
     * Тестирование по ТЗ
     *
     * @param epic эпик
     * @return список багов
     */
    public List<Bug> testEpic(Epic epic) {
        return List.of(new Bug(), new Bug());
    }

    /**
     * Тестирование исправления по багу
     * @param bug баг
     * @return список багов
     */
    public List<Bug> testBug(Bug bug){
        return List.of(new Bug(), new Bug());
    }
}
