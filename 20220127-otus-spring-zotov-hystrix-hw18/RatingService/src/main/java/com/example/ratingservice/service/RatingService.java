package com.example.ratingservice.service;

import com.example.ratingservice.domain.BookRating;

import java.util.Optional;

/**
 * @author Created by ZotovES on 30.01.2022
 * Класс управления рейтингом книги
 */
public interface RatingService {
    /**
     * Вычисление рейтинга книги
     * @param bookId ид книги
     * @return рейтинг книги
     */
    Optional<BookRating> calculateRatings(Long bookId);
}
