package ru.zotov.hw8.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw8.dao.AuthorRepository;
import ru.zotov.hw8.domain.Author;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 08.11.2021
 */
@DisplayName("Тестирование сервиса авторов")
@SpringBootTest(classes = AuthorServiceImpl.class)
class AuthorServiceImplTest {
    @MockBean private AuthorRepository authorRepository;
    @Autowired AuthorServiceImpl authorService;

    @Test
    @DisplayName("Сохранение")
    void saveTest() {
        when(authorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Author author = new Author(null, "test");
        Author result = authorService.save(author);

        assertNotNull(result);

        verify(authorRepository).save(any());
    }

    @Test
    @DisplayName("Поиск по ид")
    void findByIdTest() {
        Author author = new Author("1", "test");
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.findById("1");

        assertNotNull(result);

        verify(authorRepository).findById(any());
    }

    @Test
    @DisplayName("Удаление по ид")
    void deleteByIdTest() {
        authorService.deleteById("1");

        verify(authorRepository).deleteWithConstraintsById(any());
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findByAllTest() {
        Author author = new Author("1", "test");
        when(authorRepository.findAll()).thenReturn(List.of(author));

        List<Author> result = authorService.findByAll();

        assertThat(result).isNotNull().asList().isNotEmpty();

        verify(authorRepository).findAll();
    }

    @Test
    @DisplayName("Поиск автора по ФИО")
    void findByFioTest() {
        Author author = new Author("1", "test");
        when(authorRepository.findByFio(anyString())).thenReturn(List.of(author));

        List<Author> result = authorService.findByFio("test");

        assertThat(result).isNotNull().asList().isNotEmpty();

        verify(authorRepository).findByFio(anyString());
    }

    @Test
    @DisplayName("Поиск авторов по списку ид")
    void findByIdInTest() {
        Author author = new Author("1", "test");
        when(authorRepository.findByIdIn(any())).thenReturn(Set.of(author));

        Set<Author> result = authorService.findByIdIn(List.of("1"));

        assertFalse(result.isEmpty());

        verify(authorRepository).findByIdIn(any());
    }
}
