package ru.zotov.hw6.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw6.domain.Genre;

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
        Genre genre = new Genre(3L, "Повесть");
        Genre result = genreDao.create(genre);

        assertThat(result).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    @Rollback
    @DisplayName("Обновить жанр")
    void updateTest() {
        Genre genre = new Genre(1L, "Повесть");
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
        genreDao.deleteById(3L);

        Optional<Genre> result = genreDao.findById(3L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Найти жанры по списку ид")
    void findByIdsInTest() {
        List<Genre> result = genreDao.findByIdsIn(List.of(1L));
        assertThat(result).asList().hasSize(1)
                .allSatisfy(genre -> assertThat(genre).hasFieldOrPropertyWithValue("name", "Детектив"));
    }

    @Test
    @DisplayName("Получить все жанры")
    void getAllTest() {
        List<Genre> result = genreDao.findAll();
        assertThat(result).asList().hasSize(2)
                .anySatisfy(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", 1L)
                        .hasFieldOrPropertyWithValue("name", "Детектив"))
                .anySatisfy(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", 2L)
                        .hasFieldOrPropertyWithValue("name", "Компьютерная литература"));
    }
}
