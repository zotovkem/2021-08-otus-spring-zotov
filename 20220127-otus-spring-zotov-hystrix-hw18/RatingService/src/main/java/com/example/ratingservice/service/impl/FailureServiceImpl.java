package com.example.ratingservice.service.impl;

import com.example.ratingservice.service.FailureService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author Created by ZotovES on 30.01.2022
 * Сервис рандомно генерирует задержку ответа
 */
@Service
public class FailureServiceImpl implements FailureService {
    private static final List<Integer> delayTimerList = Arrays.asList(1, 1, 1, 1,5, 10, 100, 1000, 10000, 1000000);
    /**
     * Засыпает на рандомное время
     */
    @Override
    public Integer getRandomSleep() {
        //Помехи для проверки Hystrix
        return delayTimerList.get((int) (Math.random() * 10));
    }
}
