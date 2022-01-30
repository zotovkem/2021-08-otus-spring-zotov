package ru.zotov.hw17.domain;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;


/**
 * @author Created by ZotovES on 29.09.2021
 * Автор
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor

@Entity
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
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
