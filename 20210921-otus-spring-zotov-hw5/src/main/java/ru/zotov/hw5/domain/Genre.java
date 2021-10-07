package ru.zotov.hw5.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Created by ZotovES on 29.09.2021
 * Жанр
 */
@Data
@AllArgsConstructor
public class Genre {
    /**
     * Ид
     */
    private Long id;
    /**
     * Наименование жанра
     */
    private String name;
}
