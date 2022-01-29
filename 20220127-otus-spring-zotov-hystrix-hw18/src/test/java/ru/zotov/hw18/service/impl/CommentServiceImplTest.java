package ru.zotov.hw18.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw18.dao.CommentRepository;
import ru.zotov.hw18.domain.Author;
import ru.zotov.hw18.domain.Book;
import ru.zotov.hw18.domain.Comment;
import ru.zotov.hw18.domain.Genre;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 19.12.2021
 */
@DisplayName("Тестирование сервиса комментариев")
@SpringBootTest(classes = CommentServiceImpl.class)
class CommentServiceImplTest {
    @MockBean private CommentRepository commentRepository;
    @Autowired CommentServiceImpl commentService;

    @Test
    @DisplayName("Создание комментария")
    void createTest() {
        Book book = Book.builder()
                .id(1L)
                .name("Книга про тестирование")
                .releaseYear(2021)
                .genres(Set.of(new Genre(1L, "")))
                .authors(Set.of(new Author(1L, "")))
                .build();
        Comment comment = new Comment(1L, book, "текст комментария", "автор", ZonedDateTime.now());
        Comment savedComment = new Comment(1L, book, "текст комментария", "автор", ZonedDateTime.now());
        when(commentRepository.save(any())).thenReturn(savedComment);

        Comment result = commentService.create(comment);

        assertNotNull(result);

        verify(commentRepository).save(any());
    }

    @Test
    @DisplayName("Редактировать комментарий")
    void updateTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .releaseYear(2021)
                .genres(Set.of(new Genre(1L, "")))
                .authors(Set.of(new Author(1L, "")))
                .build();
        Comment comment = new Comment(1L, book, "текст комментария", "автор", ZonedDateTime.now());
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Comment result = commentService.update(comment);

        assertNotNull(result);

        verify(commentRepository).save(any());
    }

    @Test
    @DisplayName("Получить все комментарии ")
    void findByAllTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .releaseYear(2021)
                .genres(Set.of(new Genre(1L, "")))
                .authors(Set.of(new Author(1L, "")))
                .build();
        Comment comment = new Comment(1L, book, "текст комментария", "автор", ZonedDateTime.now());
        when(commentRepository.findAll()).thenReturn(List.of(comment));

        List<Comment> result = commentService.findByAll();

        assertThat(result).isNotNull().asList().isNotEmpty();

        verify(commentRepository).findAll();
    }

    @Test
    @DisplayName("Поиск комментария по ид")
    void findByIdTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .releaseYear(2021)
                .genres(Set.of(new Genre(1L, "")))
                .authors(Set.of(new Author(1L, "")))
                .build();
        Comment comment = new Comment(1L, book, "текст комментария", "автор", ZonedDateTime.now());
        when(commentRepository.findById(anyLong())).thenReturn(Optional.of(comment));

        Comment result = commentService.findById(1L);

        assertThat(result).isNotNull();

        verify(commentRepository).findById(anyLong());
    }

    @Test
    @DisplayName("Удаление комментариев")
    void deleteByIdsTest() {
        commentService.deleteByIds(List.of(1L, 2L));

        verify(commentRepository).deleteAllById(any());
    }
}
