package ru.zotov.hw11.handler;

import org.springframework.lang.NonNull;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author Created by ZotovES on 07.12.2021
 * Хендлера http запросов
 */
public interface BookHandler {
    /**
     * Сохранить книгу
     *
     * @return книга
     */
    HandlerFunction<ServerResponse> create();

    /**
     * Обновить книгу
     *
     * @return обновленная книга
     */
    HandlerFunction<ServerResponse> update();

    /**
     * Удалить книги по списку ид
     */
    HandlerFunction<ServerResponse> deleteByIds();

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    HandlerFunction<ServerResponse> findByAll();

    /**
     * Найти книгу по ид
     *
     * @return книга
     */
    @NonNull
    HandlerFunction<ServerResponse> findById();
}
