package ru.zotov.hw18.integration.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.stereotype.Service;
import ru.zotov.hw18.dto.BookRatingDto;
import ru.zotov.hw18.integration.RatingBookFeign;

import java.util.Optional;

/**
 * @author Created by ZotovES on 30.01.2022
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RatingBookFallbackService implements RatingBookFeign {
    private final ConcurrentMapCacheManager cacheManager;
    /**
     * Получить рейтинг из внешнего сервиса
     *
     * @param bookId ид книги
     * @return дто рейтинга книги
     */
    @Override
    public Optional<BookRatingDto> getBookRating(Long bookId) {
        log.warn("Working fall back class, get cache value");

        return Optional.ofNullable(cacheManager.getCache("bookRatings"))
                .map(cache-> cache.get(bookId,BookRatingDto.class));
    }
}
