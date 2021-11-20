package ru.zotov.hw10.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Created by ZotovES on 21.10.2021
 * Комментарий к книге
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
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

    @Override
    public String toString() {
        return String.format("Ид: %s%nКомментарий: %s%nАвтор: %s%nДата/время: %s%n" +
                        "=====================================",
                getId(), getContent(), getAuthor(), getCreateDate().format(DateTimeFormatter.RFC_1123_DATE_TIME));
    }
}
