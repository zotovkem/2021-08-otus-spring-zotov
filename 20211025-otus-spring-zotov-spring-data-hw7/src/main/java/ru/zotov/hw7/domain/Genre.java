package ru.zotov.hw7.domain;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

/**
 * @author Created by ZotovES on 29.09.2021
 * Жанр
 */
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genre")
public class Genre {
    /**
     * Ид
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Наименование жанра
     */
    @Column(name = "name")
    private String name;
    /**
     * Книги жанра
     */
    @ToString.Exclude
    @ManyToMany(mappedBy = "genres", fetch = FetchType.LAZY)
    private List<Book> books;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Genre genre = (Genre) o;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
