package ru.zotov.hw13.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Set;

/**
 * @author Created by ZotovES on 29.09.2021
 * Dto книги
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    /**
     * Ид книги
     */
    private String id;
    /**
     * Наименование книги
     */
    private String name;
    /**
     * Год издания
     */
    private int releaseYear;
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
