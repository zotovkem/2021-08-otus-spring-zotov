package ru.zotov.hw6.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import ru.zotov.hw7.dao.impl.CommentRepositoryJpaImpl;
import ru.zotov.hw7.domain.Book;
import ru.zotov.hw7.domain.Comment;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Created by ZotovES on 22.10.2021
 */
@DataJpaTest
@DisplayName("Тестирование репозитория комментариев")
@Import(CommentRepositoryJpaImpl.class)
class CommentRepositoryJpaImplTest {
    @Autowired private CommentRepositoryJpaImpl commentRepository;

    @Test
    @DisplayName("Добавить комментарий")
    void createTest() {
        Comment comment = new Comment(null, Book.builder().id(1L).build(), "testComment", "testAuthor", ZonedDateTime.now());
        Comment result = commentRepository.create(comment);

        assertThat(result).isNotNull().hasFieldOrProperty("id").isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(comment);
    }

    @Test
    @DisplayName("Редактировать комментарий")
    void updateTest() {
        Comment comment = new Comment(1L, Book.builder().id(1L).build(), "testComment", "testAuthor", ZonedDateTime.now());
        Comment result = commentRepository.update(comment);

        assertThat(result).isNotNull().usingRecursiveComparison().ignoringFields("book", "createDate").isEqualTo(comment);
    }

    @Test
    @DisplayName("Удалить комментарий")
    void deleteByIdTest() {
        commentRepository.deleteById(1L);

        Optional<Comment> result = commentRepository.findById(1L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Найти комментарий по ид")
    void findByIdTest() {
        Optional<Comment> result = commentRepository.findById(1L);

        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("content", "Вроде не чего, еще не дочитал")
                .hasFieldOrPropertyWithValue("author", "ЗотовЕС");
    }

    @Test
    @DisplayName("Получить список всех комментариев")
    void findAllTest() {
        List<Comment> result = commentRepository.findAll();

        assertThat(result).asList().hasSize(4)
                .anySatisfy(comment ->
                        assertThat(comment).hasFieldOrPropertyWithValue("id", 2L)
                                .hasFieldOrPropertyWithValue("content", "Хорошая книга")
                                .hasFieldOrPropertyWithValue("author", "ЗотовЕС"))
                .anySatisfy(comment ->
                        assertThat(comment).hasFieldOrPropertyWithValue("id", 4L)
                                .hasFieldOrPropertyWithValue("content", "Тестовый комментарий")
                                .hasFieldOrPropertyWithValue("author", "Петров"));
    }

    @Test
    @DisplayName("Поиск по ид книги")
    void findByBookId() {
        List<Comment> result = commentRepository.findByBookId(1L);

        assertThat(result).asList().hasSize(2)
                .anySatisfy(comment ->
                        assertThat(comment).hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("content", "Вроде не чего, еще не дочитал")
                                .hasFieldOrPropertyWithValue("author", "ЗотовЕС"))
                .anySatisfy(comment ->
                        assertThat(comment).hasFieldOrPropertyWithValue("id", 3L)
                                .hasFieldOrPropertyWithValue("content", "Комментарий")
                                .hasFieldOrPropertyWithValue("author", "Иванов"));
    }
}
