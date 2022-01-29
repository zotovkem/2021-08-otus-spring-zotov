package com.example.ratingservice.dao;

import com.example.ratingservice.domain.BookRating;
import com.example.ratingservice.dto.ResponseBookRating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Created by ZotovES on 29.01.2022
 */
public interface BookRatingRepository extends JpaRepository<BookRating,Long> {
    Optional<ResponseBookRating> findByBookId(Long bookId);
}
