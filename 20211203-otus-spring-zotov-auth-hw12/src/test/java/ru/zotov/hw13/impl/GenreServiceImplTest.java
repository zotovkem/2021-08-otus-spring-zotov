package ru.zotov.hw13.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw13.dao.GenreRepository;
import ru.zotov.hw13.domain.Genre;
import ru.zotov.hw13.exception.ConstrainDeleteException;
import ru.zotov.hw13.service.impl.GenreServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author Created by ZotovES on 08.11.2021
 */
@SpringBootTest(classes = GenreServiceImpl.class)
@DisplayName("Тестирование сервиса жанров")
class GenreServiceImplTest {
    @MockBean GenreRepository genreRepository;
    @Autowired GenreServiceImpl genreService;

    @Test
    @DisplayName("Сохранение")
    void saveTest() {
        Genre genre = new Genre(null, "genre");
        when(genreRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        Genre result = genreService.save(genre);

        assertNotNull(result);

        verify(genreRepository).save(any());
    }

    @Test
    @DisplayName("Поиск по ид")
    void findByIdTest() {
        Genre genre = new Genre(null, "genre");
        when(genreRepository.findById(any())).thenReturn(Optional.of(genre));

        Optional<Genre> result = genreService.findById("1");

        assertTrue(result.isPresent());

        verify(genreRepository).findById(any());
    }

    @Test
    @DisplayName("Удаление по списку ид")
    void deleteByListIdsTest() throws ConstrainDeleteException {
        genreService.deleteByListIds(List.of("1", "2"));

        verify(genreRepository).deleteWithConstraintsByIds(any());
    }

    @Test
    @DisplayName("Поиск всех жанров")
    void findAllTest() {
        Genre genre = new Genre(null, "genre");
        when(genreRepository.findAll()).thenReturn(List.of(genre));

        List<Genre> result = genreService.findAll();

        assertFalse(result.isEmpty());

        verify(genreRepository).findAll();
    }

    @Test
    @DisplayName("Поиск по списку ид")
    void findByIdInTest() {
        Genre genre = new Genre(null, "genre");
        when(genreRepository.findByIdIn(any())).thenReturn(Set.of(genre));

        Set<Genre> result = genreService.findByIdIn(List.of("1"));

        assertFalse(result.isEmpty());

        verify(genreRepository).findByIdIn(any());
    }
}
