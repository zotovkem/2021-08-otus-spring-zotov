package ru.zotov.hw13.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithMockUser;
import ru.zotov.hw13.dao.CommentRepository;
import ru.zotov.hw13.domain.Author;
import ru.zotov.hw13.domain.Book;
import ru.zotov.hw13.domain.Comment;
import ru.zotov.hw13.domain.Genre;

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
@WithMockUser("adult")
class CommentServiceImplTest {
    @MockBean private CommentRepository commentRepository;
    @MockBean private MutableAclService aclService;
    @Autowired CommentServiceImpl commentService;

    @Test
    @DisplayName("Создание комментария")
    void createTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .releaseYear(2021)
                .genres(Set.of(new Genre(1L, "")))
                .authors(Set.of(new Author(1L, "")))
                .build();
        Comment comment = new Comment(1L, book, "текст комментария", "автор", ZonedDateTime.now());
        when(commentRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(aclService.createAcl(any())).thenReturn(mock(MutableAcl.class));

        Comment result = commentService.create(comment);

        assertNotNull(result);

        verify(commentRepository).save(any());
        verify(aclService).createAcl(any());
        verify(aclService).updateAcl(any());
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
        Book book = Book.builder().name("Книга про тестирование")
                .releaseYear(2021)
                .genres(Set.of(new Genre(1L, "")))
                .authors(Set.of(new Author(1L, "")))
                .build();
        Comment commentOne = new Comment(1L, book, "текст комментария", "автор", ZonedDateTime.now());
        Comment commentTwo = new Comment(2L, book, "второй комментарий", "другой автор", ZonedDateTime.now());
        when(commentRepository.findAllById(anyList())).thenReturn(List.of(commentOne, commentTwo));

        commentService.deleteByIds(List.of(1L, 2L));

        verify(commentRepository).findAllById(anyList());
        verify(aclService, times(2)).deleteAcl(any(), anyBoolean());
        verify(commentRepository, times(2)).delete(any());
    }
}
