package ru.zotov.hw14.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import ru.zotov.hw14.dao.GenreRepositoryMongo;
import ru.zotov.hw14.domain.GenreMongo;
import ru.zotov.hw14.exception.ConstrainDeleteException;
import ru.zotov.hw14.service.impl.GenreServiceImpl;

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
    @MockBean GenreRepositoryMongo genreRepository;
    @Autowired GenreServiceImpl genreService;

    @Test
    @DisplayName("Сохранение")
    void saveTest() {
        GenreMongo genre = new GenreMongo(null, "genre");
        when(genreRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        GenreMongo result = genreService.save(genre);

        assertNotNull(result);

        verify(genreRepository).save(any());
    }

    @Test
    @DisplayName("Поиск по ид")
    void findByIdTest() {
        GenreMongo genre = new GenreMongo(null, "genre");
        when(genreRepository.findById(any())).thenReturn(Optional.of(genre));

        Optional<GenreMongo> result = genreService.findById("1");

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
        GenreMongo genre = new GenreMongo(null, "genre");
        when(genreRepository.findAll()).thenReturn(List.of(genre));

        List<GenreMongo> result = genreService.findAll();

        assertFalse(result.isEmpty());

        verify(genreRepository).findAll();
    }

    @Test
    @DisplayName("Поиск по списку ид")
    void findByIdInTest() {
        GenreMongo genre = new GenreMongo(null, "genre");
        when(genreRepository.findByIdIn(any())).thenReturn(Set.of(genre));

        Set<GenreMongo> result = genreService.findByIdIn(List.of("1"));

        assertFalse(result.isEmpty());

        verify(genreRepository).findByIdIn(any());
    }
}
