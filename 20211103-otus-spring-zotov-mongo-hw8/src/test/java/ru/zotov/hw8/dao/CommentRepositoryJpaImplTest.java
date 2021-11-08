package ru.zotov.hw8.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Comment;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Created by ZotovES on 22.10.2021
 */
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan(value = {"ru.zotov.hw8.converters", "ru.zotov.hw8.dao"})
@DisplayName("Тестирование репозитория комментариев")
class CommentRepositoryJpaImplTest {
    @Autowired private CommentRepository commentRepository;

    @Test
    @DisplayName("Добавить комментарий")
    void createTest() {
        Comment comment = new Comment(null, Book.builder().id("1").build(), "testComment", "testAuthor", ZonedDateTime.now());
        Comment result = commentRepository.save(comment);

        assertThat(result).isNotNull().hasFieldOrProperty("id").isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(comment);
    }

    @Test
    @DisplayName("Редактировать комментарий")
    void updateTest() {
        Comment comment = new Comment("1", Book.builder().id("1").build(), "testComment", "testAuthor", ZonedDateTime.now());
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
}
