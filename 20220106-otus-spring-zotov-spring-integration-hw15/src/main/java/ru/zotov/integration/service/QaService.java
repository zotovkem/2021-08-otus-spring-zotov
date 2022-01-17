package ru.zotov.integration.service;

import ru.zotov.integration.domain.Bug;
import ru.zotov.integration.domain.Epic;

import java.util.List;

/**
 * @author Created by ZotovES on 17.01.2022
 * Сервис тестирования
 */
public interface QaService {
    /**
     * Тестирование по ТЗ
     *
     * @param epic эпик
     * @return список багов
     */
     List<Bug> testEpic(Epic epic) ;

    /**
     * Тестирование исправления по багу
     * @param bug баг
     * @return список багов
     */
    Epic testBug(Bug bug);
}
