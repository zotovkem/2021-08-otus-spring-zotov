package ru.zotov.hw5.domain;

import lombok.*;

import javax.persistence.*;

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
     * Книга
     */
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    /**
     * Содержимое комментария
     */
    @Column(name = "content")
    private String content;


}
