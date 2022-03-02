package ru.zotov.auth.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.lang.NonNull;

import java.io.IOException;

/**
 * @author Created by ZotovES on 27.01.2021
 * Утилиты для работы с Json
 */
public class JsonUtils {
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new Jdk8Module())
            .registerModule(new JavaTimeModule());

    /**
     * Преобразует объект в JSON
     */
    @NonNull
    public static String asJsonString(@NonNull final Object obj) {
        try {
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Парсит JSON
     */
    @NonNull
    public static <T> T fromString(@NonNull String json, Class<T> clazz) {
        try {
            return mapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Преобразет мапу в объект
     *
     * @param map   мапа
     * @param clazz класс объекта
     * @param <T>   дженерик исходящего типа
     * @return полученный объект
     */
    @NonNull
    public static <T> T fromMap(@NonNull Object map, Class<T> clazz) {
        try {
            return mapper.convertValue(map, clazz);
        } catch (ClassCastException e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * Преобразует JSON в объект JsonNode
     *
     * @param value json в виде строки
     * @return JsonNode
     */
    public static JsonNode toJsonNode(String value) {
        try {
            return mapper.readTree(value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Преобразует JSON в объект JsonNode
     *
     * @param value json в виде строки
     * @return JsonNode
     */
    public static ObjectNode toJsonObjectNode(Object value) {
        try {
            return (ObjectNode) mapper.readTree(asJsonString(value));
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
    }

    /**
     * Создать пустой ObjectNode
     *
     * @return ObjectNode
     */
    @NonNull
    public static ObjectNode emptyObjectNode() {
        return mapper.createObjectNode();
    }

    /**
     * Клонирование объекта
     *
     * @param value объект
     * @param <T>   класс объекта
     * @return инстанс клона
     */
    @SuppressWarnings("unchecked")
    public static <T> T clone(T value) {
        return fromString(asJsonString(value), (Class<T>) value.getClass());
    }
}
