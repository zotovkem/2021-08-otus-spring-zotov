package ru.zotov.hw10.dao.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw10.dao.BookRepositoryCustom;
import ru.zotov.hw10.domain.Author;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Comment;
import ru.zotov.hw10.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author Created by ZotovES on 08.11.2021
 * Реализация кастомного репозитория книг
 */
@RequiredArgsConstructor
public class BookRepositoryCustomImpl implements BookRepositoryCustom {
    private final MongoTemplate mongoTemplate;

    /**
     * Каскадное удаление зависимых сущностей
     *
     * @param bookId ид книги
     */
    @Override
    public void cascadeDeleteById(String bookId) {
        Optional.ofNullable(mongoTemplate.findAndRemove(new Query(where("id").is(bookId)), Book.class)).ifPresent(book -> {
            List<String> commentsIds = book.getComments().stream()
                    .map(Comment::getId)
                    .collect(Collectors.toList());
            mongoTemplate.remove(new Query(where("id").in(commentsIds)), Comment.class);
        });
    }

    /**
     * Поиск по наименованию жанра
     *
     * @param name наименование жанра
     * @return список книг
     */
    @Override
    public List<Book> findByGenreName(String name) {
        Query query = Query.query(where("name").regex("^" + name + ".*", "i"));
        List<String> genreIds = mongoTemplate.find(query, Genre.class).stream().map(Genre::getId).collect(Collectors.toList());
        return mongoTemplate.find(Query.query(where("genres.$id").in(genreIds)), Book.class);
    }

    /**
     * Поиск по ФИО автора книги
     *
     * @param fio ФИО автора
     * @return список книг
     */
    @Override
    public List<Book> findByAuthorFio(String fio) {
        Query query = Query.query(where("fio").regex("^" + fio + ".*", "i"));
        List<String> authorIds = mongoTemplate.find(query, Author.class).stream().map(Author::getId).collect(Collectors.toList());
        return mongoTemplate.find(Query.query(where("authors.$id").in(authorIds)), Book.class);
    }
}
