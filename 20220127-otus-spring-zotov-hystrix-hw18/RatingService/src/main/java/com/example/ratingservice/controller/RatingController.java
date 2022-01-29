package com.example.ratingservice.controller;

import com.example.ratingservice.dao.BookRatingRepository;
import com.example.ratingservice.dto.ResponseBookRating;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 11.09.2021
 * Контроллер рейтинга книг
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "ratings")
public class RatingController {
    private static final List<Integer> delayTimerList = Arrays.asList(1, 1, 1, 1, 10, 100, 1000, 10000, 1000000);
    private final BookRatingRepository bookRatingRepository;

    /**
     * Получить кошелек игрока
     *
     * @return рейтинг книги
     */
    @GetMapping("/book/{id}")
    public ResponseEntity<ResponseBookRating> getBookRating(@PathVariable Long id) throws InterruptedException {
        //Помехи для проверки Hystrix
        Thread.sleep(delayTimerList.get((int) (Math.random() * 10)));

        return bookRatingRepository.findByBookId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
