package ru.zotov.hw11.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 29.09.2021
 * Книга
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "book")
public class Book {
    /**
     * Ид книги
     */
    @Id
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
    @Builder.Default
    private List<Author> authors = new ArrayList<>();
    /**
     * Жанры
     */
    @Builder.Default
    private List<Genre> genres = new ArrayList<>();
    /**
     * Комментарии
     */
    @Builder.Default
    private List<Comment> comments = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("Ид: %s%nНаименование книги: %s%nГод издательства: %s%n" +
                        "Авторы: %s%n" +
                        "Жанры: %s%n" +
                        "=====================================", getId(), getName(), getReleaseYear(),
                getAuthors().stream().map(Author::getFio).collect(Collectors.joining(", ")),
                getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Book book = (Book) o;
        return releaseYear == book.releaseYear && Objects.equals(id, book.id) && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseYear);
    }
}
