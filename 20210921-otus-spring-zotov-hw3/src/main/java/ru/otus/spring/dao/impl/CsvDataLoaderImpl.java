package ru.otus.spring.dao.impl;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import ru.otus.spring.exception.CannotReadFileException;
import ru.otus.spring.dao.DataLoader;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Реализация загрузчика данных для репозитория из CSV файла
 */
@Component
@RequiredArgsConstructor
public class CsvDataLoaderImpl implements DataLoader {
    /**
     * Загрузка данных в список из CSV файла
     *
     * @param type     тип класса
     * @param resource ресурс
     * @param <T>      дженирик класса
     * @return список загруженных объектов
     */
    @Override
    public <T> List<T> loadObjectList(Class<T> type, Resource resource) {
        try (InputStream inputStream = resource.getInputStream()) {
            MappingIterator<T> readValues = getMappingIterator(type, inputStream);

            return readValues.readAll();
        } catch (Exception e) {
            throw new CannotReadFileException("Error read file " + resource.getFilename(),e);
        }
    }

    private <T> MappingIterator<T> getMappingIterator(Class<T> type, InputStream inputStream) throws IOException {
        CsvMapper mapper = new CsvMapper();
        CsvSchema bootstrapSchema = mapper.schemaWithHeader().withHeader().withColumnSeparator(';');
        return mapper.readerFor(type).with(bootstrapSchema).readValues(inputStream);
    }
}
