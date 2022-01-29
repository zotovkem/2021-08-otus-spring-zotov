package com.example.ratingservice.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 29.01.2022
 * Рейтинг книги
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "rating_book")
public class BookRating {
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "rating")
    private Long rating;
}
