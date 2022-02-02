package com.example.ratingservice.service;

/**
 * @author Created by ZotovES on 30.01.2022
 *  Сервис рандомно генерирует задержку ответа
 */
public interface FailureService {
    /**
     * Засыпает на рандомное время
     */
    Integer getRandomSleep() ;
}
