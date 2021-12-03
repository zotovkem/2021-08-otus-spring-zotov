package ru.zotov.hw12.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 29.09.2021
 * Dto жанра
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GenreDto {
    /**
     * Ид
     */
    private String id;
    /**
     * Наименование жанра
     */
    private String name;
}
