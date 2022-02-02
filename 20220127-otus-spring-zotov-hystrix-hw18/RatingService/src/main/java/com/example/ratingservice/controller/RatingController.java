package com.example.ratingservice.controller;

import com.example.ratingservice.domain.BookRating;
import com.example.ratingservice.dto.BookRatingDto;
import com.example.ratingservice.service.FailureService;
import com.example.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.function.Function;

/**
 * @author Created by ZotovES on 11.09.2021
 * Контроллер рейтинга книг
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "ratings")
public class RatingController {
    private final RatingService ratingService;
    private final FailureService failureService;

    /**
     * Получить рейтинг книги
     *
     * @return рейтинг книги
     */
    @PostMapping("/book/{id}")
    public ResponseEntity<BookRatingDto> getBookRating(@PathVariable Long id) throws InterruptedException {
//        Засыпает на рандомное время для проверки Hystrix
        Thread.sleep(failureService.getRandomSleep());

        return ratingService.calculateRatings(id)
                .map(mappingToDto())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Маппинг из сущности в дто
     */
    private Function<BookRating, BookRatingDto> mappingToDto() {
        return bookRating -> new BookRatingDto(bookRating.getBookId(), bookRating.getRating());
    }
}
