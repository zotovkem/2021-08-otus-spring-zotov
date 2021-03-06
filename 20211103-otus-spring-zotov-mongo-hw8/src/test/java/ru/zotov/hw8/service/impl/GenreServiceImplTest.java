package ru.zotov.hw8.service.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw8.dao.GenreRepository;
import ru.zotov.hw8.domain.Genre;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
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
    @DisplayName("Удаление по ид")
    void deleteByIdTest() {
        genreService.deleteById("1");

        verify(genreRepository).deleteWithConstraintsById(any());
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
    @DisplayName("Поиск по наименованию")
    void findByNameTest() {
        Genre genre = new Genre(null, "genre");
        when(genreRepository.findByName(anyString())).thenReturn(List.of(genre));

        List<Genre> result = genreService.findByName("test");

        assertFalse(result.isEmpty());

        verify(genreRepository).findByName(anyString());
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
