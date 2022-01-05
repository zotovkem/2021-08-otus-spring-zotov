package ru.zotov.hw14.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;
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
public class BookMongo {
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
    @DBRef
    @Builder.Default
    private Set<AuthorMongo> authors = new HashSet<>();
    /**
     * Жанры
     */
    @DBRef
    @Builder.Default
    private Set<GenreMongo> genres = new HashSet<>();
    /**
     * Комментарии
     */
    @Builder.Default
    private List<CommentMongo> comments = new ArrayList<>();

    @Override
    public String toString() {
        return String.format("Ид: %s%nНаименование книги: %s%nГод издательства: %s%n" +
                        "Авторы: %s%n" +
                        "Жанры: %s%n" +
                        "=====================================", getId(), getName(), getReleaseYear(),
                getAuthors().stream().map(AuthorMongo::getFio).collect(Collectors.joining(", ")),
                getGenres().stream().map(GenreMongo::getName).collect(Collectors.joining(", ")));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BookMongo book = (BookMongo) o;
        return releaseYear == book.releaseYear && Objects.equals(id, book.id) && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, releaseYear);
    }
}
