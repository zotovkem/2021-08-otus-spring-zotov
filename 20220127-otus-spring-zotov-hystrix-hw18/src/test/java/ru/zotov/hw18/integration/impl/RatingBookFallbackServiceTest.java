package ru.zotov.hw18.integration.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cache.Cache;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import ru.zotov.hw18.dto.BookRatingDto;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 30.01.2022
 */
@DisplayName("Тестирование сервиса получения рейтинга из кэша ")
@SpringBootTest(classes = RatingBookFallbackServiceImpl.class)
class RatingBookFallbackServiceTest {
    @MockBean ConcurrentMapCacheManager cacheManager;
    @Autowired
    RatingBookFallbackServiceImpl ratingBookFallbackService;

    @Test
    @DisplayName("Получить рейтинг книги")
    void getBookRatingTestTest() {
        Cache cache = mock(Cache.class);
        when(cacheManager.getCache("bookRatings")).thenReturn(cache);
        BookRatingDto bookRatingDto = new BookRatingDto(1L, 1L);
        when(cache.get(1L,BookRatingDto.class)).thenReturn(bookRatingDto);

        Optional<BookRatingDto> result = ratingBookFallbackService.getBookRating(1L);

        assertThat(result).isPresent().get().usingRecursiveComparison().isEqualTo(bookRatingDto);

        verify(cacheManager).getCache("bookRatings");
        verify(cache).get(1L,BookRatingDto.class);
    }
}
