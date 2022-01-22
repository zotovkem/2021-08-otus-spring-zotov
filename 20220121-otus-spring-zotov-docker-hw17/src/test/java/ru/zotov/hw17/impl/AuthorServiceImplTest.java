package ru.zotov.hw17.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw17.dao.AuthorRepository;
import ru.zotov.hw17.domain.Author;
import ru.zotov.hw17.service.impl.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
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
        Author author = new Author(1L, "test");
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));

        Optional<Author> result = authorService.findById(1L);

        assertNotNull(result);

        verify(authorRepository).findById(any());
    }

    @Test
    @DisplayName("Удаление по списку ид")
    void deleteByListIdsTest() {
        authorService.deleteByListIds(List.of(1L, 2L));

        verify(authorRepository).deleteAllByIdInBatch(any());
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findByAllTest() {
        Author author = new Author(1L, "test");
        when(authorRepository.findAll()).thenReturn(List.of(author));

        List<Author> result = authorService.findByAll();

        assertThat(result).isNotNull().asList().isNotEmpty();

        verify(authorRepository).findAll();
    }
}
