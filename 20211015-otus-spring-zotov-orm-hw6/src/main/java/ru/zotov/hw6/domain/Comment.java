package ru.zotov.hw6.domain;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author Created by ZotovES on 21.10.2021
 * Комментарий к книге
 */
@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "comment_for_book")
public class Comment {
    /**
     * Ид
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @EqualsAndHashCode.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    /**
     * Содержимое комментария
     */
    @Column(name = "content")
    private String content;
    /**
     * Автор комментария
     */
    @Column(name = "author")
    private String author;
    /**
     * Дата создания
     */
    @Column(name = "create_date")
    private ZonedDateTime createDate;

    @Override
    public String toString() {
        return String.format("Комментарий: %s%n" +
                        "Автор: %s%n" +
                        "Дата/время: %s%n" +
                        "=====================================",
                getContent(), getAuthor(), getCreateDate().format(DateTimeFormatter.RFC_1123_DATE_TIME));
    }
}
