package ru.zotov.hw5.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * @author Created by ZotovES on 29.09.2021
 * Книга
 */
@Data
@AllArgsConstructor
public class Book {
    /**
     * Ид книги
     */
    private long id;
    /**
     * Наименование книги
     */
    private String name;
    /**
     * Год издания
     */
    private int releaseYear;
    /**
     * Авторы
     */
    private List<Author> authors;
    /**
     * Жанры
     */
    private List<Genre> genres;
}