package ru.zotov.hw11.dto;

import lombok.*;

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
@EqualsAndHashCode(exclude = {"authors", "genres", "comments"})
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
