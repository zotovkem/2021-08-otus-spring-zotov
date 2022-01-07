package ru.zotov.hw14.domain.jpa;

import lombok.*;
import org.springframework.lang.Nullable;

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
public class AuthorJpa {
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
        AuthorJpa author = (AuthorJpa) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
