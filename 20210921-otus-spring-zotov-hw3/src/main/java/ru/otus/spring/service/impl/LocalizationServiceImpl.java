package ru.otus.spring.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import ru.otus.spring.service.LocalizationService;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * @author Created by ZotovES on 22.09.2021
 * Реализация сервиса локализации
 */
@Service
public class LocalizationServiceImpl implements LocalizationService {
    private final MessageSource messageSource;
    private final Locale currentLocal;

    public LocalizationServiceImpl(MessageSource messageSource, @Value("${app.current-locale:ru}") Locale currentLocal) {
        this.messageSource = messageSource;
        this.currentLocal = currentLocal;
    }

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
        return messageSource.getMessage(tag, args.toArray(), currentLocal);
    }
}
