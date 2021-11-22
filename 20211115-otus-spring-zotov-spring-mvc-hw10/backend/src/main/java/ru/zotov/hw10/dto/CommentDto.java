package ru.zotov.hw10.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;

/**
 * @author Created by ZotovES on 21.10.2021
 * Комментарий к книге
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDto {
    /**
     * Ид
     */
    private String id;
    /**
     * Содержимое комментария
     */
    private String content;
    /**
     * Автор комментария
     */
    private String author;
    /**
     * Дата создания
     */
    private ZonedDateTime createDate;
}
