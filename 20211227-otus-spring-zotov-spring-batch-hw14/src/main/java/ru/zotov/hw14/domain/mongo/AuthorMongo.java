package ru.zotov.hw14.domain.mongo;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

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

@Document(collection = "author")
public class AuthorMongo {
    /**
     * Ид автора
     */
    @Id
    private String id;
    /**
     * ФИО автора
     */
    private String fio;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthorMongo author = (AuthorMongo) o;
        return Objects.equals(id, author.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
