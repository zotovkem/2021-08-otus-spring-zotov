package ru.otus.spring.service;

import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author Created by ZotovES on 22.09.2021
 * Сервис локализации сообщений
 */
public interface LocalizationService {
    /**
     * Получить текст
     *
     * @param tag тэг локализации
     * @return локализованный текст
     */
    String getLocalizationTextByTag(@NonNull String tag);

    /**
     * Получить текст
     *
     * @param tag  тэг локализации
     * @param args параметры для вставки в текст сообщения
     * @return локализованный текст
     */
    String getLocalizationTextByTag(@NonNull String tag, List<String> args);
}
