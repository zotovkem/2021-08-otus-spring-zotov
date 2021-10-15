package ru.zotov.hw5.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Created by ZotovES on 29.09.2021
 * Автор
 */
@Data
@AllArgsConstructor
public class Author {
    /**
     * Ид автора
     */
    private Long id;
    /**
     * ФИО автора
     */
    private String fio;
}
