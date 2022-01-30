package com.example.ratingservice.controller;

import com.example.ratingservice.domain.BookRating;
import com.example.ratingservice.dto.BookRatingDto;
import com.example.ratingservice.service.RatingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

/**
 * @author Created by ZotovES on 11.09.2021
 * Контроллер рейтинга книг
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "ratings")
public class RatingController {
    private static final List<Integer> delayTimerList = Arrays.asList(1, 1, 1, 1,5, 10, 100, 1000, 10000, 1000000);
    private final RatingService ratingService;

    /**
     * Получить рейтинг книги
     *
     * @return рейтинг книги
     */
    @PostMapping("/book/{id}")
    public ResponseEntity<BookRatingDto> getBookRating(@PathVariable Long id) throws InterruptedException {
        //Помехи для проверки Hystrix
        Thread.sleep(delayTimerList.get((int) (Math.random() * 10)));

        return ratingService.calculateRatings(id)
                .map(mappingToDto())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Маппинг из сущности в дто
     */
    private Function<BookRating,BookRatingDto> mappingToDto() {
        return bookRating-> new BookRatingDto(bookRating.getBookId(), bookRating.getRating());
    }
}
