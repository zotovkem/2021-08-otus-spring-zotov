package ru.zotov.hw8.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

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

@Document(collection = "comment_for_book")
public class Comment {
    /**
     * Ид
     */
    @Id
    private String id;
    /**
     * Книга
     */
    @EqualsAndHashCode.Exclude
    private Book book;
    /**
     * Содержимое комментария
     */
    private String content;
    /**
     * Автор комментария
     */
    @Field(name = "author")
    private String author;
    /**
     * Дата создания
     */
    @Field(name = "create_date")
    private ZonedDateTime createDate;


    @Override
    public String toString() {
        return String.format("Ид: %s%nКомментарий: %s%nАвтор: %s%nДата/время: %s%n" +
                        "=====================================",
                getId(), getContent(), getAuthor(), getCreateDate().format(DateTimeFormatter.RFC_1123_DATE_TIME));
    }
}
