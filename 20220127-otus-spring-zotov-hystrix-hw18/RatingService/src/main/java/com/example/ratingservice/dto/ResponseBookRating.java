package com.example.ratingservice.dto;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.web.ProjectedPayload;

/**
 * @author Created by ZotovES on 29.01.2022
 * Проекция рейтинга книги
 */
public interface ResponseBookRating {
    @Value(value = "#{target.bookId}")
    Long getBookId();

    @Value(value = "#{target.rating}")
    Long getRating();
}
