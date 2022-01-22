package ru.zotov.integration.service;

/**
 * @author Created by ZotovES on 17.01.2022
 * Сервис аналитика
 */
public interface AnalystService {
    /**
     * Аналитик создает ТЗ на фичу
     * @param epicName имя эпика
     */
    void createEpic(String epicName);
}
