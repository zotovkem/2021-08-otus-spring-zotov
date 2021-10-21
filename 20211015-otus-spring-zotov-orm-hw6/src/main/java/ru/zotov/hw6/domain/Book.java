package ru.zotov.hw6.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Created by ZotovES on 29.09.2021
 * Книга
 */
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
public class Book {
    /**
     * Ид книги
     */
    @Id
    @Column(name = "id")
    private Long id;
    /**
     * Наименование книги
     */
    @Column(name = "name")
    private String name;
    /**
     * Год издания
     */
    @Column(name = "release_year")
    private int releaseYear;
    /**
     * Авторы
     */
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mtm_book_author", joinColumns = {@JoinColumn(name = "book_id")},
               inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private List<Author> authors = new ArrayList<>();
    /**
     * Жанры
     */
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mtm_book_genres", joinColumns = {@JoinColumn(name = "book_id")},
               inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private List<Genre> genres = new ArrayList<>();

//TODO
//    public String toString() {
//        return String.format("Ид: %s%nНаименование книги: %s%nГод издательства: %s%n" +
//                        "Авторы: %s%n" +
//                        "Жанры: %s%n" +
//                        "=====================================", getId(), getName(), getReleaseYear(),
//                getAuthors().stream().map(Author::getFio).collect(Collectors.joining(", ")),
//                getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")));
//    }

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
