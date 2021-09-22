package ru.otus.spring.service;

import org.springframework.core.io.Resource;

import java.util.List;

/**
 * @author Created by ZotovES on 22.09.2021
 */
public interface DataLoaderService {
    /**
     * Загрузка данных в список из CSV файла
     *
     * @param type     тип класса
     * @param resource ресурс
     * @param <T>      дженирик класса
     * @return список загруженных объектов
     */
    <T> List<T> loadObjectList(Class<T> type, Resource resource);
}
