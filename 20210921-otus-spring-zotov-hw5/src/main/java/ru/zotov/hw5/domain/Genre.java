package ru.zotov.hw5.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private long id;
    /**
     * Наименование жанра
     */
    private String name;
}
