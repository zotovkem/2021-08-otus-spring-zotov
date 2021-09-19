package ru.otus.spring.service;

/**
 * @author Created by ZotovES on 19.09.2021
 * Сервис ввода значений с консоли
 */
public interface InputService {
    /**
     * Получить строковое значение
     *
     * @return строковое значение
     */
    String getConsoleStrValue();

    /**
     * Получить числовое значение
     *
     * @return числовое значение
     */
    Integer getConsoleIntValue();
}
