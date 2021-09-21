package ru.otus.spring.utils;

import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.exception.CannotReadFileException;

import java.io.InputStream;
import java.util.List;

/**
 * @author Created by ZotovES on 30.08.2021
 * Утилитарный класс для загрузки данных из CSV файла
 */
public class DataLoaderCsv {
    private DataLoaderCsv() {}

    public static <T> List<T> loadObjectList(Class<T> type, String fileName) {
        try {
            CsvMapper mapper = new CsvMapper();
            InputStream inputStream = new ClassPathResource(fileName).getInputStream();
            CsvSchema bootstrapSchema = mapper.schemaWithHeader().withHeader().withColumnSeparator(',');
            MappingIterator<T> readValues = mapper.readerFor(type).with(bootstrapSchema).readValues(inputStream);
            return readValues.readAll();
        } catch (Exception e) {
            throw new CannotReadFileException("Error read file " + fileName);
        }
    }
}
