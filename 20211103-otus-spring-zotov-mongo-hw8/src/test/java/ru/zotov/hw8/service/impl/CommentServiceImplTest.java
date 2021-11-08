package ru.zotov.hw8.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw8.dao.CommentRepository;
import ru.zotov.hw8.domain.Author;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Comment;
import ru.zotov.hw8.domain.Genre;
import ru.zotov.hw8.service.BookService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 25.10.2021
 */
@SpringBootTest(classes = CommentServiceImpl.class)
@DisplayName("Тестирование сервиса комментариев")
class CommentServiceImplTest {
    @MockBean private CommentRepository commentRepository;
    @MockBean private BookService bookService;
    @Autowired CommentServiceImpl commentService;


    @Test
    @DisplayName("Добавить комментарий")
    void createTest() {
        Genre genreOne = new Genre("1", "Сказки");
        Genre genreTwo = new Genre("2", "Повесть");
        Author authorOne = new Author("1", "Иванов");
        Author authorTwo = new Author("2", "Петров");
        Book book = Book.builder().name("Книга про тестирование")
                .id("1")
                .releaseYear(2021)
                .genres(Set.of(genreOne, genreTwo))
                .authors(Set.of(authorOne, authorTwo))
                .build();
        when(bookService.findById(anyString())).thenReturn(Optional.of(book));
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Comment comment = new Comment(null, Book.builder().id("1").build(), "testComment", "testAuthor", ZonedDateTime.now());
        Comment result = commentService.create(comment);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(comment);

        verify(bookService).findById(anyString());
        verify(commentRepository).save(comment);
    }

    @Test
    @DisplayName("Редактировать комментарий")
    void updateTest() {
        Comment comment = new Comment("1", Book.builder().id("1").build(), "testComment", "testAuthor", ZonedDateTime.now());
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Comment result = commentService.update(comment);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
                .usingRecursiveComparison().isEqualTo(comment);

        verify(commentRepository).findById(any());
        verify(commentRepository).save(comment);
    }

    @Test
    @DisplayName("Удалить комментарий по ид")
    void deleteByIdTest() {

        commentService.deleteById("1");

        verify(commentRepository).deleteById(any());
    }

    @Test
    @DisplayName("Найти комментарий по ид")
    void findByIdTest() {
        Comment comment = new Comment("1", Book.builder().id("1").build(), "testComment", "testAuthor", ZonedDateTime.now());
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        Comment result = commentService.findById("1");

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id").usingRecursiveComparison().isEqualTo(comment);

        verify(commentRepository).findById(any());
    }

    @Test
    @DisplayName("Получить список всех комментариев")
    void findAllTest() {
        Comment comment = new Comment("1", Book.builder().id("1").build(), "testComment", "testAuthor", ZonedDateTime.now());
        when(commentRepository.findAll()).thenReturn(List.of(comment));

        List<Comment> result = commentService.findAll();

        assertThat(result).isNotNull().asList().allSatisfy(c ->
                assertThat(c).hasNoNullFieldsOrPropertiesExcept("id").usingRecursiveComparison().isEqualTo(comment)
        );

        verify(commentRepository).findAll();
    }

    @Test
    @DisplayName("Получить список комментариев по ид книги")
    void findByBookIdTest() {
        Comment comment = new Comment("1", Book.builder().id("1").build(), "testComment", "testAuthor", ZonedDateTime.now());
        when(commentRepository.findByBookId(anyString())).thenReturn(List.of(comment));

        List<Comment> result = commentService.findByBookId("1");

        assertThat(result).isNotNull().asList().allSatisfy(c ->
                assertThat(c).hasNoNullFieldsOrPropertiesExcept("id").usingRecursiveComparison().isEqualTo(comment)
        );

        verify(commentRepository).findByBookId(anyString());
    }
}
