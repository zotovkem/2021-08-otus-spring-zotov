package ru.zotov.hw18.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 29.09.2021
 * Dto детальной информации о книги
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailDto {
    /**
     * Ид книги
     */
    private Long id;
    /**
     * Наименование книги
     */
    private String name;
    /**
     * Год издания
     */
    private int releaseYear;
    /**
     * Рейтинг книги
     */
    private Long rating;
    /**
     * Авторы
     */
    private Set<AuthorDto> authors;
    /**
     * Жанры
     */
    private Set<GenreDto> genres;
    /**
     * Комментарии
     */
    private List<CommentDto> comments;
}
