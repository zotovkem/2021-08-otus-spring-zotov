package ru.otus.spring.dao;

import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author Created by ZotovES on 22.09.2021
 * Утилита загрузки данных из ресурсов в кастомный репозиторий
 */
public interface DataLoader {
    /**
     * Загрузка данных в список из файла ресурсов
     *
     * @param type     тип класса
     * @param resource ресурс
     * @param <T>      дженирик класса
     * @return список загруженных объектов
     */
    <T> List<T> loadObjectList(Class<T> type, Resource resource);
}
