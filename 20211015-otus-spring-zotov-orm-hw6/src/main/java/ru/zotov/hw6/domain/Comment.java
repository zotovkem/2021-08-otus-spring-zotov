package ru.zotov.hw6.domain;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;

/**
 * @author Created by ZotovES on 21.10.2021
 * Комментарий к книге
 */
@Getter
@Setter
@Entity
@ToString
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

}
