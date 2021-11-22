package ru.zotov.hw8.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw10.dao.BookRepository;
import ru.zotov.hw10.domain.Author;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Genre;
import ru.zotov.hw10.service.AuthorService;
import ru.zotov.hw10.service.GenreService;
import ru.zotov.hw10.service.impl.BookServiceImpl;

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
    @MockBean private AuthorService authorService;
    @MockBean private GenreService genreService;
    @Autowired BookServiceImpl bookService;

    @Test
    @DisplayName("Создать книгу")
    void createTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .releaseYear(2021)
                .genres(Set.of(new Genre("1", "")))
                .authors(Set.of(new Author("1", "")))
                .build();
        when(bookDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.save(book);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(book);

        verify(bookDao).save(book);
        verify(authorService).findByIdIn(any());
        verify(genreService).findByIdIn(any());
    }

    @Test
    @DisplayName("Редактировать книгу")
    void updateTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .id("1")
                .releaseYear(2021)
                .genres(Set.of(new Genre("1", "")))
                .authors(Set.of(new Author("1", "")))
                .build();
        when(bookDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Book result = bookService.save(book);

        assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(book);

        verify(bookDao).save(book);
    }

    @Test
    @DisplayName("Удалить по ид")
    void deleteByIdTest() {
        bookService.deleteByIds(List.of("1"));

        verify(bookDao).deleteByIds(anyList());
    }

    @Test
    @DisplayName("Получить все книги")
    void findByAllTest() {
        Genre genreOne = new Genre("1", "Сказки");
        Genre genreTwo = new Genre("2", "Повесть");
        Author authorOne = new Author("1", "Иванов");
        Author authorTwo = new Author("2", "Петров");
        Book bookOne = Book.builder().name("Книга про тестирование")
                .id("1")
                .releaseYear(2021)
                .genres(Set.of(genreOne))
                .authors(Set.of(authorOne))
                .build();
        Book bookTwo = Book.builder().name("Сказки")
                .id("2")
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

        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.findById("1");

        assertThat(result).isPresent().get().usingRecursiveComparison().isEqualTo(book);

        verify(bookDao).findById(anyString());
    }

    @Test
    @DisplayName("Поиск по наименование книги")
    void findByNameTest() {
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

        when(bookDao.findByName(anyString())).thenReturn(List.of(book));

        List<Book> result = bookService.findByName("test");

        assertThat(result).isNotNull().asList().isNotEmpty()
                .allSatisfy(b -> assertThat(b).usingRecursiveComparison().isEqualTo(b));

        verify(bookDao).findByName(anyString());
    }

    @Test
    @DisplayName("Поиск книг по наименованию жанра")
    void findByGenreNameTest() {
        Book book = Book.builder().name("Книга про тестирование")
                .id("1")
                .releaseYear(2021)
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
        when(bookDao.findByAuthorFio(anyString())).thenReturn(List.of(book));

        List<Book> result = bookService.findByAuthorFio("test");

        assertThat(result).isNotNull().asList().isNotEmpty()
                .allSatisfy(b -> assertThat(b).usingRecursiveComparison().isEqualTo(b));

        verify(bookDao).findByAuthorFio(anyString());
    }
}
