package ru.zotov.hw13.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithMockUser;
import ru.zotov.hw13.dao.BookRepository;
import ru.zotov.hw13.domain.Author;
import ru.zotov.hw13.domain.Book;
import ru.zotov.hw13.domain.Genre;
import ru.zotov.hw13.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

/**
 * @author Created by ZotovES on 11.10.2021
 */
@DisplayName("Тестирование сервиса книг")
@WithMockUser("admin")
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {
    @MockBean private BookRepository bookDao;
    @MockBean private MutableAclService aclService;
    @Autowired BookServiceImpl bookService;

    @Test
    @DisplayName("Создать книгу")
    void createTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .id(1L)
                .releaseYear(2021)
                .genres(Set.of(new Genre(1L, "")))
                .authors(Set.of(new Author(1L, "")))
                .build();
        when(bookDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));
        when(aclService.createAcl(any())).thenReturn(mock(MutableAcl.class));

        Book result = bookService.create(book);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(book);

        verify(bookDao).save(book);
        verify(aclService).createAcl(any());
        verify(aclService).updateAcl(any());
    }

    @Test
    @DisplayName("Редактировать книгу")
    void updateTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .id(1L)
                .releaseYear(2021)
                .genres(Set.of(new Genre(1L, "")))
                .authors(Set.of(new Author(1L, "")))
                .build();
        when(bookDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.update(book);

        assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(book);

        verify(bookDao).save(book);
    }

    @Test
    @DisplayName("Удалить по ид")
    void deleteByIdTest() {
        bookService.deleteByIds(List.of(1L));

        verify(bookDao).deleteAllByIdInBatch(anyList());
        verify(aclService).deleteAcl(any(), anyBoolean());
    }

    @Test
    @DisplayName("Получить все книги")
    void findByAllTest() {
        Genre genreOne = new Genre(1L, "Сказки");
        Genre genreTwo = new Genre(2L, "Повесть");
        Author authorOne = new Author(1L, "Иванов");
        Author authorTwo = new Author(2L, "Петров");
        Book bookOne = Book.builder().name("Книга про тестирование")
                .id(1L)
                .releaseYear(2021)
                .genres(Set.of(genreOne))
                .authors(Set.of(authorOne))
                .build();
        Book bookTwo = Book.builder().name("Сказки")
                .id(2L)
                .releaseYear(2021)
                .genres(Set.of(genreTwo))
                .authors(Set.of(authorTwo))
                .build();

        when(bookDao.findAll()).thenReturn(List.of(bookOne, bookTwo));

        List<Book> result = bookService.findByAll();

        assertThat(result).isNotNull().asList().isNotEmpty()
                .anySatisfy(book -> assertThat(book).usingRecursiveComparison().isEqualTo(bookOne))
                .anySatisfy(book -> assertThat(book).usingRecursiveComparison().isEqualTo(bookTwo));

        verify(bookDao).findAll();
    }

    @Test
    @DisplayName("Поиск по ид книги")
    void findByIdTest() {
        Genre genreOne = new Genre(1L, "Сказки");
        Genre genreTwo = new Genre(2L, "Повесть");
        Author authorOne = new Author(1L, "Иванов");
        Author authorTwo = new Author(2L, "Петров");
        Book book = Book.builder().name("Книга про тестирование")
                .id(1L)
                .releaseYear(2021)
                .genres(Set.of(genreOne, genreTwo))
                .authors(Set.of(authorOne, authorTwo))
                .build();

        when(bookDao.findById(anyLong())).thenReturn(Optional.of(book));

        Book result = bookService.findById(1L);

        assertThat(result).usingRecursiveComparison().isEqualTo(book);

        verify(bookDao).findById(anyLong());
    }
}
