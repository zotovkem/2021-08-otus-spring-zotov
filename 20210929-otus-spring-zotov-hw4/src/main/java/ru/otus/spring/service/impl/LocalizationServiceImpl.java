package ru.otus.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.spring.config.ApplicationProperties;
import ru.otus.spring.service.LocalizationService;

import java.util.Collections;
import java.util.List;

/**
 * @author Created by ZotovES on 22.09.2021
 * Реализация сервиса локализации
 */
@Service
@RequiredArgsConstructor
public class LocalizationServiceImpl implements LocalizationService {
    private final ApplicationProperties applicationProperties;
    private final MessageSource messageSource;

    /**
     * Получить текст
     *
     * @param tag тэг локализации
     * @return локализованный текст
     */
    @Override
    public String getLocalizationTextByTag(@NonNull String tag) {
        return getLocalizationTextByTag(tag, Collections.emptyList());
    }

    /**
     * Получить текст
     *
     * @param tag  тэг локализации
     * @param args параметры для вставки в текст сообщения
     * @return локализованный текст
     */
    @Override
    public String getLocalizationTextByTag(@NonNull String tag, List<String> args) {
        return messageSource.getMessage(tag, args.toArray(), applicationProperties.getCurrentLocal());
    }
}
