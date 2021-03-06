package ru.zotov.hw6.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw6.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataJpaTest
@DisplayName("Тестирование репозитория жанров")
@Import(GenreDaoRepositoryJpaImpl.class)
class GenreDaoRepositoryJpaImplTest {
    @Autowired private GenreDaoRepositoryJpaImpl genreDao;

    @Test
    @DisplayName("Создать жанр")
    void createTest() {
        Genre genre = new Genre(3L, "Повесть", Collections.emptyList());
        Genre result = genreDao.create(genre);

        assertThat(result).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    @Rollback
    @DisplayName("Обновить жанр")
    void updateTest() {
        Genre genre = new Genre(1L, "Повесть", Collections.emptyList());
        Genre result = genreDao.update(genre);

        assertThat(result).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    @DisplayName("Получить жанр по ид")
    void getByIdTest() {
        Optional<Genre> result = genreDao.findById(1L);
        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("name", "Детектив");
    }

    @Test
    @Rollback
    @DisplayName("Удалить жанр по ид")
    void deleteByIdTest() {
        Optional<Genre> genre = genreDao.findById(3L);
        assertThat(genre).isPresent();

        genreDao.delete(genre.get());

        Optional<Genre> result = genreDao.findById(3L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Получить все жанры")
    void getAllTest() {
        List<Genre> result = genreDao.findAll();

        assertThat(result).asList().hasSize(3)
                .anySatisfy(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", 1L)
                        .hasFieldOrPropertyWithValue("name", "Детектив"))
                .anySatisfy(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", 2L)
                        .hasFieldOrPropertyWithValue("name", "Компьютерная литература"));
    }
}
