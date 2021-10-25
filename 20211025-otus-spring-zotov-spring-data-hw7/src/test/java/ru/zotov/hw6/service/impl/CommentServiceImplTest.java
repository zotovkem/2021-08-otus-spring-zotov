package ru.zotov.hw6.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw6.dao.CommentRepository;
import ru.zotov.hw6.domain.Book;
import ru.zotov.hw6.domain.Comment;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 25.10.2021
 */
@SpringBootTest(classes = CommentServiceImpl.class)
@DisplayName("Тестирование сервиса комментариев")
class CommentServiceImplTest {
    @MockBean private CommentRepository commentRepository;
    @Autowired CommentServiceImpl commentService;


    @Test
    @DisplayName("Добавить комментарий")
    void createTest() {
        when(commentRepository.create(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Comment comment = new Comment(null, Book.builder().id(1L).build(), "testComment", "testAuthor", ZonedDateTime.now());
        Comment result = commentService.create(comment);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(comment);

        verify(commentRepository).create(comment);
    }

    @Test
    @DisplayName("Редактировать комментарий")
    void updateTest() {
        Comment comment = new Comment(1L, Book.builder().id(1L).build(), "testComment", "testAuthor", ZonedDateTime.now());
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));
        when(commentRepository.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Comment result = commentService.update(comment);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
                .usingRecursiveComparison().isEqualTo(comment);

        verify(commentRepository).findById(any());
        verify(commentRepository).update(comment);
    }

    @Test
    @DisplayName("Удалить комментарий по ид")
    void deleteByIdTest() {
        commentService.deleteById(1L);

        verify(commentRepository).deleteById(1L);
    }

    @Test
    @DisplayName("Найти комментарий по ид")
    void findByIdTest() {
        Comment comment = new Comment(1L, Book.builder().id(1L).build(), "testComment", "testAuthor", ZonedDateTime.now());
        when(commentRepository.findById(any())).thenReturn(Optional.of(comment));

        Comment result = commentService.findById(1L);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id").usingRecursiveComparison().isEqualTo(comment);

        verify(commentRepository).findById(any());
    }

    @Test
    @DisplayName("Получить список всех комментариев")
    void findAllTest() {
        Comment comment = new Comment(1L, Book.builder().id(1L).build(), "testComment", "testAuthor", ZonedDateTime.now());
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
        Comment comment = new Comment(1L, Book.builder().id(1L).build(), "testComment", "testAuthor", ZonedDateTime.now());
        when(commentRepository.findByBookId(1L)).thenReturn(List.of(comment));

        List<Comment> result = commentService.findByBookId(1L);

        assertThat(result).isNotNull().asList().allSatisfy(c ->
                assertThat(c).hasNoNullFieldsOrPropertiesExcept("id").usingRecursiveComparison().isEqualTo(comment)
        );

        verify(commentRepository).findByBookId(1L);
    }
}
