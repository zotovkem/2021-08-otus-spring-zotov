package ru.zotov.hw14.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw14.dao.AuthorRepositoryMongo;
import ru.zotov.hw14.domain.AuthorMongo;
import ru.zotov.hw14.exception.ConstrainDeleteException;
import ru.zotov.hw14.service.impl.AuthorServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    @MockBean private AuthorRepositoryMongo authorRepository;
    @Autowired AuthorServiceImpl authorService;

    @Test
    @DisplayName("Сохранение")
    void saveTest() {
        when(authorRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        AuthorMongo author = new AuthorMongo(null, "test");
        AuthorMongo result = authorService.save(author);

        assertNotNull(result);

        verify(authorRepository).save(any());
    }

    @Test
    @DisplayName("Поиск по ид")
    void findByIdTest() {
        AuthorMongo author = new AuthorMongo("1", "test");
        when(authorRepository.findById(any())).thenReturn(Optional.of(author));

        Optional<AuthorMongo> result = authorService.findById("1");

        assertNotNull(result);

        verify(authorRepository).findById(any());
    }

    @Test
    @DisplayName("Удаление по списку ид")
    void deleteByListIdsTest() throws ConstrainDeleteException {
        authorService.deleteByListIds(List.of("1", "2"));

        verify(authorRepository).deleteWithConstraintsByIds(any());
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findByAllTest() {
        AuthorMongo author = new AuthorMongo("1", "test");
        when(authorRepository.findAll()).thenReturn(List.of(author));

        List<AuthorMongo> result = authorService.findByAll();

        assertThat(result).isNotNull().asList().isNotEmpty();

        verify(authorRepository).findAll();
    }

    @Test
    @DisplayName("Поиск авторов по списку ид")
    void findByIdInTest() {
        AuthorMongo author = new AuthorMongo("1", "test");
        when(authorRepository.findByIdIn(any())).thenReturn(Set.of(author));

        Set<AuthorMongo> result = authorService.findByIdIn(List.of("1"));

        assertFalse(result.isEmpty());

        verify(authorRepository).findByIdIn(any());
    }
}
