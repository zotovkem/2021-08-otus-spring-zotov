package ru.zotov.hw10.dao;

import org.springframework.data.repository.query.Param;
import ru.zotov.hw10.domain.Book;

import java.util.List;

/**
 * @author Created by ZotovES on 08.11.2021
 * Кастомный репозиторий книг
 */
public interface BookRepositoryCustom {
    /**
     * Удаление книг по списку ид
     *
     * @param ids список ид книг
     */
    void deleteByIds(List<String> ids);

    /**
     * Поиск по наименованию жанра
     *
     * @param name наименование жанра
     * @return список книг
     */
    List<Book> findByGenreName(@Param("name") String name);

    /**
     * Поиск по ФИО автора книги
     *
     * @param fio ФИО автора
     * @return список книг
     */
    List<Book> findByAuthorFio(@Param("fio") String fio);
}
