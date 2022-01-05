package ru.zotov.hw14.domain.jpa;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Created by ZotovES on 29.09.2021
 * Жанр
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "genre")
public class GenreJpa {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        GenreJpa genre = (GenreJpa) o;
        return Objects.equals(id, genre.id) && Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}