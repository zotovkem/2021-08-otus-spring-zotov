package ru.zotov.hw6.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw7.dao.AuthorRepository;
import ru.zotov.hw7.dao.BookRepository;
import ru.zotov.hw7.dao.GenreRepository;
import ru.zotov.hw7.domain.Author;
import ru.zotov.hw7.domain.Book;
import ru.zotov.hw7.domain.Genre;
import ru.zotov.hw7.service.impl.BookServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 11.10.2021
 */
@DisplayName("Тестирование сервиса книг")
@SpringBootTest(classes = BookServiceImpl.class)
class BookServiceImplTest {
    @MockBean private BookRepository bookDao;
    @MockBean private AuthorRepository authorDao;
    @MockBean private GenreRepository genreDao;
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
        when(bookDao.create(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.create(book);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(book);

        verify(bookDao).create(book);
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
        when(bookDao.update(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.update(book);

        assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(book);

        verify(bookDao).update(book);
    }

    @Test
    @DisplayName("Удалить по ид")
    void deleteByIdTest() {
        bookService.deleteById(1L);

        verify(bookDao).deleteById(1L);
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

        Optional<Book> result = bookService.findById(1L);

        assertThat(result).isPresent().get().usingRecursiveComparison().isEqualTo(book);

        verify(bookDao).findById(anyLong());
    }

    @Test
    @DisplayName("Поиск по наименование книги")
    void findByNameTest() {
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

        when(bookDao.findByName(anyString())).thenReturn(List.of(book));

        List<Book> result = bookService.findByName("test");

        assertThat(result).isNotNull().asList().isNotEmpty()
                .allSatisfy(b -> assertThat(b).usingRecursiveComparison().isEqualTo(b));

        verify(bookDao).findByName(anyString());
    }

    @Test
    @DisplayName("Поиск книг по наименованию жанра")
    void findByGenreNameTest() {
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

        when(bookDao.findByGenreName(anyString())).thenReturn(List.of(book));

        List<Book> result = bookService.findByGenreName("test");

        assertThat(result).isNotNull().asList().isNotEmpty()
                .allSatisfy(b -> assertThat(b).usingRecursiveComparison().isEqualTo(b));

        verify(bookDao).findByGenreName(anyString());
    }

    @Test
    @DisplayName("Поиск книг по ФИО автора")
    void findByAuthorFioTest() {
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

        when(bookDao.findByAuthorFio(anyString())).thenReturn(List.of(book));

        List<Book> result = bookService.findByAuthorFio("test");

        assertThat(result).isNotNull().asList().isNotEmpty()
                .allSatisfy(b -> assertThat(b).usingRecursiveComparison().isEqualTo(b));

        verify(bookDao).findByAuthorFio(anyString());
    }
}
