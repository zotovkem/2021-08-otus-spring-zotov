package ru.zotov.hw7.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

/**
 * @author Created by ZotovES on 29.09.2021
 * Автор
 */
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "author")
public class Author {
    /**
     * Ид автора
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * ФИО автора
     */
    @Column(name = "fio")
    private String fio;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Author author = (Author) o;
        return Objects.equals(id, author.id) && Objects.equals(fio, author.fio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fio);
    }
}