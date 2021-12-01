package ru.zotov.hw11.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.zotov.hw11.domain.Genre;
import ru.zotov.hw11.exception.ConstrainDeleteException;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan(value = {"ru.zotov.hw11.dao"})
@DisplayName("Тестирование репозитория жанров")
class GenreDaoRepositoryJpaImplTest {
    @Autowired private GenreRepository genreDao;

    @Test
    @DisplayName("Создать жанр")
    void createTest() {
        Genre genre = new Genre(null, "Повесть");
        Mono<Genre> result = genreDao.save(genre);
        StepVerifier
                .create(result)
                .assertNext(resGenre -> assertThat(resGenre).usingRecursiveComparison().isEqualTo(genre))
                .expectComplete()
                .verify();
    }

    @Test
    @Rollback
    @DisplayName("Обновить жанр")
    void updateTest() {
        Genre genre = new Genre("1", "Повесть");
        Mono<Genre> result = genreDao.save(genre);

        StepVerifier
                .create(result)
                .assertNext(resGenre -> assertThat(resGenre).usingRecursiveComparison().isEqualTo(genre))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Получить жанр по ид")
    void getByIdTest() {
        Mono<Genre> result = genreDao.findById("1");

        StepVerifier
                .create(result)
                .assertNext(resGenre -> assertThat(resGenre).hasFieldOrPropertyWithValue("name", "Детектив"))
                .expectComplete()
                .verify();
    }

    @Test
    @Rollback
    @DisplayName("Ошибка удаления жанра")
    void deleteWithConstraintsByIdsExceptionTest() {
        Mono<Void> result = genreDao.deleteWithConstraintsByIds(List.of("3"));

        StepVerifier
                .create(result)
                .expectError(ConstrainDeleteException.class)
                .verify();
    }

    @Test
    @Rollback
    @DisplayName("Удалить жанр по ид")
    void deleteWithConstraintsByIdsTest() throws ConstrainDeleteException {
        String genreId = "9";
        Mono<String> genre = genreDao.findById(genreId).map(Genre::getId);
        StepVerifier
                .create(genre)
                .assertNext(Assertions::assertNotNull)
                .expectComplete()
                .verify();

        StepVerifier
                .create(genreDao.deleteWithConstraintsByIds(List.of(genreId)))
                .expectNextCount(0)
                .verifyComplete();

        Mono<Genre> result = genreDao.findById(genreId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Получить все жанры")
    void getAllTest() {
        Flux<Genre> result = genreDao.findAll();

        StepVerifier.create(
                        result.filter(g -> "2".equals(g.getId()) || "5".equals(g.getId())).sort(Comparator.comparing(Genre::getId)))
                .assertNext(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", "2")
                        .hasFieldOrPropertyWithValue("name", "Компьютерная литература"))
                .assertNext(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", "5")
                        .hasFieldOrPropertyWithValue("name", "Программирование"))
                .expectComplete()
                .verify();
    }
}
