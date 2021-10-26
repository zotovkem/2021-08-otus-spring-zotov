package ru.zotov.hw7.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 29.09.2021
 * Книга
 */
@Getter
@Setter
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "book")
@NamedEntityGraph(name = "book-graph", attributeNodes = {@NamedAttributeNode("comments")/*,@NamedAttributeNode("authors")*/})
public class Book {
    /**
     * Ид книги
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "mtm_book_author", joinColumns = {@JoinColumn(name = "book_id")},
               inverseJoinColumns = {@JoinColumn(name = "author_id")})
    private Set<Author> authors = new HashSet<>();
    /**
     * Жанры
     */
    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Fetch(FetchMode.SUBSELECT)
    @ManyToMany(targetEntity = Genre.class, fetch = FetchType.LAZY)
    @JoinTable(name = "mtm_book_genre", joinColumns = {@JoinColumn(name = "book_id")},
               inverseJoinColumns = {@JoinColumn(name = "genre_id")})
    private Set<Genre> genres = new HashSet<>();

    @Builder.Default
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "book", fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    public String toString() {
        return String.format("Ид: %s%nНаименование книги: %s%nГод издательства: %s%n" +
                        "Авторы: %s%n" +
                        "Жанры: %s%n" +
                        "Комментарии: %n%s%n" +
                        "=====================================", getId(), getName(), getReleaseYear(),
                getAuthors().stream().map(Author::getFio).collect(Collectors.joining(", ")),
                getGenres().stream().map(Genre::getName).collect(Collectors.joining(", ")),
                getComments().stream().map(getCommentStringFunction()).collect(Collectors.joining(System.lineSeparator())));
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

    private Function<Comment, String> getCommentStringFunction() {
        return comment -> String.format("%s \"%s\" %s", comment.getCreateDate().toLocalDate(), comment.getContent(),
                comment.getAuthor());
    }
}
