package ru.zotov.hw18.integration;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ru.zotov.hw18.dto.BookRatingDto;
import ru.zotov.hw18.integration.impl.RatingBookFallbackServiceImpl;

import java.util.Optional;

/**
 * @author Created by ZotovES on 29.01.2022
 * Feign клиент для интеграции с внещним сервисом рейтингов книг
 */
@FeignClient(name = "bookRatingServiceClient", url = "${integration.rating.url}"
        , fallback = RatingBookFallbackServiceImpl.class
)
@CachePut(value = "bookRatings")
public interface RatingBookFeignClient {
    /**
     * Получить рейтинг из внешнего сервиса
     * @param bookId ид книги
     * @return дто рейтинга книги
     */
    @PostMapping("/ratings/book/{id}")
    Optional<BookRatingDto> getBookRating(@PathVariable(name = "id") Long bookId);
}
