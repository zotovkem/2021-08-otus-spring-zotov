package ru.zotov.hw18.integration;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ru.zotov.hw18.dto.BookRatingDto;

import java.util.Optional;

/**
 * @author Created by ZotovES on 29.01.2022
 * Feign клиент для интеграции с внещним сервисом рейтингов книг
 */
@FeignClient(name = "bookRatingServiceClient", url = "${integration.rating.url}")
public interface RatingBookFeign {
    /**
     * Получить рейтинг из внешнего сервиса
     * @param bookId ид книги
     * @return дто рейтинга книги
     */
    @GetMapping("/ratings/book/{id}")
    Optional<BookRatingDto> getBookRating(@PathVariable(name = "id") Long bookId);
}
