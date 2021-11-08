package ru.zotov.hw8.dao;

/**
 * @author Created by ZotovES on 08.11.2021
 * Кастомный репозиторий книг
 */
public interface BookRepositoryCustom {
    /**
     * Каскадное удаление зависимых сущностей
     *
     * @param bookId ид книги
     */
    void cascadeDeleteById(String bookId);
}
