package com.example.ratingservice;

import com.example.ratingservice.dao.BookRatingRepository;
import com.example.ratingservice.domain.BookRating;
import com.example.ratingservice.service.impl.RatingServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@DisplayName("Тестирование сервиса рейтингов")
@SpringBootTest(classes = RatingServiceImpl.class)
class RatingServiceApplicationTests {
    @MockBean private BookRatingRepository bookRatingRepository;
    @Autowired RatingServiceImpl bookRatingService;

    @Test
    @DisplayName("Тестирование сервиса рейтингов")
    void calculateRatingsTest() {
        long currentRating = 1L;
        BookRating bookRating = new BookRating(1L, 1L, currentRating);
        when(bookRatingRepository.findByBookId(anyLong())).thenReturn(Optional.of(bookRating));
        when(bookRatingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Optional<BookRating> result = bookRatingService.calculateRatings(1L);

        assertThat(result).isPresent().map(BookRating::getRating).hasValue(currentRating+1);

        verify(bookRatingRepository).findByBookId(any());
        verify(bookRatingRepository).save(any());
    }

}
