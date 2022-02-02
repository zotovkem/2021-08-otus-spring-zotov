package com.example.ratingservice.service.impl;

import com.example.ratingservice.dao.BookRatingRepository;
import com.example.ratingservice.domain.BookRating;
import com.example.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * @author Created by ZotovES on 30.01.2022
 * Реализвция сервиса рейтинга книг
 */
@Service
@RequiredArgsConstructor
public class RatingServiceImpl implements RatingService {
    private final BookRatingRepository bookRatingRepository;
    /**
     * Вычисление рейтинга книги
     *
     * @param bookId ид книги
     * @return рейтинг книги
     */
    @Override
    @Transactional
    public Optional<BookRating> calculateRatings(Long bookId) {
        Optional<BookRating> bookRating = bookRatingRepository.findByBookId(bookId);

        bookRating.ifPresent(br-> {
            br.setRating(br.getRating()+1);
            bookRatingRepository.save(br);
        });

        return bookRating;
    }
}

