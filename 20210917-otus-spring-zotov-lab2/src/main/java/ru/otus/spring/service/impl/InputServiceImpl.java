package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.service.InputService;

import java.util.Scanner;

/**
 * @author Created by ZotovES on 19.09.2021
 * Реализация сервиса ввода значений с консоли
 */
@Service
public class InputServiceImpl implements InputService {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Получить строковое значение
     *
     * @return строковое значение
     */
    public String getConsoleStrValue() {
        return scanner.nextLine().trim().toLowerCase();
    }

    /**
     * Получить числовое значение
     *
     * @return числовое значение
     */
    public Integer getConsoleIntValue() {
        return scanner.nextInt();
    }
}
