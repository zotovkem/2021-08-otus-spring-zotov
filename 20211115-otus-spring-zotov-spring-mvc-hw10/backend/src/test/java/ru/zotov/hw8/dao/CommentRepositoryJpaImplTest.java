package ru.zotov.hw8.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import ru.zotov.hw10.dao.CommentRepository;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Comment;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.data.mongodb.core.query.Criteria.where;

/**
 * @author Created by ZotovES on 22.10.2021
 */
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan(value = {"ru.zotov.hw8.converters", "ru.zotov.hw8.dao"})
@DisplayName("Тестирование репозитория комментариев")
class CommentRepositoryJpaImplTest {
    @Autowired private CommentRepository commentRepository;
    @Autowired private MongoTemplate mongoTemplate;

    @Test
    @DisplayName("Добавить комментарий")
    void createTest() {
        Comment comment = new Comment(null, "testComment", "testAuthor", ZonedDateTime.now());
        Comment result = commentRepository.save(comment);

        assertThat(result).isNotNull().hasFieldOrProperty("id").isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(comment);
    }

    @Test
    @DisplayName("Редактировать комментарий")
    void updateTest() {
        Comment comment = new Comment("1", "testComment", "testAuthor", ZonedDateTime.now());
        Comment result = commentRepository.save(comment);

        assertThat(result).isNotNull().usingRecursiveComparison().ignoringFields("book", "createDate").isEqualTo(comment);
    }

    @Test
    @DisplayName("Удалить комментарий")
    void deleteByIdTest() {
        Optional<Comment> comment = commentRepository.findById("1");
        assertThat(comment).isPresent();

        commentRepository.delete(comment.get());

        Optional<Comment> result = commentRepository.findById("1");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Найти комментарий по ид")
    void findByIdTest() {
        Optional<Comment> result = commentRepository.findById("2");

        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("content", "Хорошая книга")
                .hasFieldOrPropertyWithValue("author", "ЗотовЕС");
    }

    @Test
    @DisplayName("Получить список всех комментариев")
    void findAllTest() {
        List<Comment> result = commentRepository.findAll();

        assertThat(result).asList()
                .anySatisfy(comment ->
                        assertThat(comment).hasFieldOrPropertyWithValue("id", "2")
                                .hasFieldOrPropertyWithValue("content", "Хорошая книга")
                                .hasFieldOrPropertyWithValue("author", "ЗотовЕС"))
                .anySatisfy(comment ->
                        assertThat(comment).hasFieldOrPropertyWithValue("id", "8")
                                .hasFieldOrPropertyWithValue("content", "Тестовый комментарий")
                                .hasFieldOrPropertyWithValue("author", "Петров"));
    }

    @Test
    @DisplayName("Каскадное редактирование комментария")
    public void cascadeSaveTest() {
        Comment comment = new Comment("2", "testComment", "testAuthor", ZonedDateTime.now());
        Comment result = commentRepository.cascadeSave(comment);

        assertThat(result).isNotNull().usingRecursiveComparison().ignoringFields("createDate").isEqualTo(comment);

        List<Comment> comments = Optional.of(comment.getId())
                .map(id -> new Query(where("comments.id").is(id)))
                .map(query -> mongoTemplate.findOne(query, Book.class))
                .map(Book::getComments).stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
        assertThat(comments).isNotNull().asList()
                .anySatisfy(c -> assertThat(c).hasFieldOrPropertyWithValue("id", "2")
                        .hasFieldOrPropertyWithValue("content", "testComment")
                        .hasFieldOrPropertyWithValue("author", "testAuthor"));
    }

    @Test
    @DisplayName("Каскадное удаление комментария")
    public void cascadeDeleteTest() {
        commentRepository.cascadeDelete("2");

        List<Comment> comments = Optional.of("2")
                .map(id -> new Query(where("comments.id").is(id)))
                .map(query -> mongoTemplate.findOne(query, Book.class))
                .map(Book::getComments).stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
        assertThat(comments).isNotNull().asList()
                .noneSatisfy(c -> assertThat(c).hasFieldOrPropertyWithValue("id", "2")
                        .hasFieldOrPropertyWithValue("content", "testComment")
                        .hasFieldOrPropertyWithValue("author", "testAuthor"));

    }
}
