package ru.otus.spring.service.impl;

import org.springframework.stereotype.Service;
import ru.otus.spring.service.InputService;

import java.util.Scanner;

/**
 * @author Created by ZotovES on 19.09.2021
 */
@Service
public class InputServiceImpl implements InputService {
    private static final Scanner scanner = new Scanner(System.in);

    public String getConsoleStrValue() {
        return scanner.nextLine().trim().toLowerCase();
    }

    public Integer getConsoleIntValue() {
        return scanner.nextInt();
    }
}
