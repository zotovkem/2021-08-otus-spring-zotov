package ru.otus.spring.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Created by ZotovES on 30.08.2021
 * Утилитарный класс для загрузки данных из CSV файла
 */
public class DataLoaderCsv {
    private DataLoaderCsv() {}

    public static <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvMapper mapper = new CsvMapper();
            File file = new ClassPathResource(fileName).getFile();
            CsvSchema bootstrapSchema = mapper.schemaWithHeader().withHeader().withColumnSeparator(',');
            MappingIterator<T> readValues = mapper.readerFor(type).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return Collections.emptyList();
        }
    }
}
