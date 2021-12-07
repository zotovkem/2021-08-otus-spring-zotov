package ru.zotov.hw11.dao;

import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Created by ZotovES on 11.11.2021
 * Кастомный репозиторий авторов
 */
public interface AuthorRepositoryCustom {
    /**
     * Удаление авторов с проверкой зависимых сущностей
     *
     * @param ids список ид авторов
     */
    Mono<Void> deleteWithConstraintsByIds(List<String> ids);
}
