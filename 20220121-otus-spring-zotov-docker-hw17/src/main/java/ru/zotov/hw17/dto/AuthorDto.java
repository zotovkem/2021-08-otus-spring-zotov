package ru.zotov.hw17.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * @author Created by ZotovES on 29.09.2021
 * Dto автора
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthorDto {
    /**
     * Ид автора
     */
    private Long id;
    /**
     * ФИО автора
     */
    private String fio;
}
