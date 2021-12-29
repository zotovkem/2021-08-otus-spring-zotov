package ru.zotov.hw14.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw14.dao.BookRepositoryMongo;
import ru.zotov.hw14.domain.AuthorMongo;
import ru.zotov.hw14.domain.BookMongo;
import ru.zotov.hw14.domain.GenreMongo;
import ru.zotov.hw14.service.AuthorService;
import ru.zotov.hw14.service.GenreService;
import ru.zotov.hw14.service.impl.BookServiceImpl;

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
    @MockBean private BookRepositoryMongo bookDao;
    @MockBean private AuthorService authorService;
    @MockBean private GenreService genreService;
    @Autowired BookServiceImpl bookService;

    @Test
    @DisplayName("Создать книгу")
    void createTest() {
        BookMongo book = BookMongo.builder().name("Книга про тестирование")
                .releaseYear(2021)
                .genres(Set.of(new GenreMongo("1", "")))
                .authors(Set.of(new AuthorMongo("1", "")))
                .build();
        when(bookDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        BookMongo result = bookService.save(book);

        assertThat(result).isNotNull().hasNoNullFieldsOrPropertiesExcept("id")
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(book);

        verify(bookDao).save(book);
        verify(authorService).findByIdIn(any());
        verify(genreService).findByIdIn(any());
    }

    @Test
    @DisplayName("Редактировать книгу")
    void updateTest() {
        BookMongo book = BookMongo.builder().name("Книга про тестирование")
                .id("1")
                .releaseYear(2021)
                .genres(Set.of(new GenreMongo("1", "")))
                .authors(Set.of(new AuthorMongo("1", "")))
                .build();
        when(bookDao.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        BookMongo result = bookService.save(book);

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
        GenreMongo genreOne = new GenreMongo("1", "Сказки");
        GenreMongo genreTwo = new GenreMongo("2", "Повесть");
        AuthorMongo authorOne = new AuthorMongo("1", "Иванов");
        AuthorMongo authorTwo = new AuthorMongo("2", "Петров");
        BookMongo bookOne = BookMongo.builder().name("Книга про тестирование")
                .id("1")
                .releaseYear(2021)
                .genres(Set.of(genreOne))
                .authors(Set.of(authorOne))
                .build();
        BookMongo bookTwo = BookMongo.builder().name("Сказки")
                .id("2")
                .releaseYear(2021)
                .genres(Set.of(genreTwo))
                .authors(Set.of(authorTwo))
                .build();

        when(bookDao.findAll()).thenReturn(List.of(bookOne, bookTwo));

        List<BookMongo> result = bookService.findByAll();

        assertThat(result).isNotNull().asList().isNotEmpty()
                .anySatisfy(book -> assertThat(book).usingRecursiveComparison().isEqualTo(bookOne))
                .anySatisfy(book -> assertThat(book).usingRecursiveComparison().isEqualTo(bookTwo));

        verify(bookDao).findAll();
    }

    @Test
    @DisplayName("Поиск по ид книги")
    void findByIdTest() {
        GenreMongo genreOne = new GenreMongo("1", "Сказки");
        GenreMongo genreTwo = new GenreMongo("2", "Повесть");
        AuthorMongo authorOne = new AuthorMongo("1", "Иванов");
        AuthorMongo authorTwo = new AuthorMongo("2", "Петров");
        BookMongo book = BookMongo.builder().name("Книга про тестирование")
                .id("1")
                .releaseYear(2021)
                .genres(Set.of(genreOne, genreTwo))
                .authors(Set.of(authorOne, authorTwo))
                .build();

        when(bookDao.findById(anyString())).thenReturn(Optional.of(book));

        Optional<BookMongo> result = bookService.findById("1");

        assertThat(result).isPresent().get().usingRecursiveComparison().isEqualTo(book);

        verify(bookDao).findById(anyString());
    }
}
