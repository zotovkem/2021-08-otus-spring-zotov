package ru.otus.spring.dao;

import ru.otus.spring.utils.DataLoaderCsv;

import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Аьстрактный класс CSV репозитория
 */
public abstract class CsvFileDao<T> {
    protected List<T> data;

    protected CsvFileDao(String pathCsvFile, Class<T> type) {
        data = DataLoaderCsv.loadObjectList(type, pathCsvFile);
    }
}
