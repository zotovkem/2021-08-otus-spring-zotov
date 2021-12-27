package ru.zotov.hw11.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author Created by ZotovES on 21.10.2021
 * Dto комментария к книге
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
    private Date createDate;
}
