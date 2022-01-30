package com.example.ratingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.web.ProjectedPayload;

/**
 * @author Created by ZotovES on 29.01.2022
 * Dto рейтинга книги
 */
@Data
@AllArgsConstructor
public class BookRatingDto {
    private Long bookId;
    private Long rating;
}
