package ru.zotov.hw18.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author Created by ZotovES on 29.01.2022
 * DTO рейтинга книги
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRatingDto {
    Long bookId;
    Long rating;
}
