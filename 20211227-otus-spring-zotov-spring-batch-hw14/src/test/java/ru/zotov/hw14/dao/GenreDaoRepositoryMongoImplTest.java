package ru.zotov.hw14.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw14.domain.GenreMongo;
import ru.zotov.hw14.exception.ConstrainDeleteException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;


/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan(value = {"ru.zotov.hw14.converters", "ru.zotov.hw14.dao"})
@DisplayName("Тестирование репозитория жанров")
class GenreDaoRepositoryMongoImplTest {
    @Autowired private GenreRepositoryMongo genreDao;

    @Test
    @DisplayName("Создать жанр")
    void createTest() {
        GenreMongo genre = new GenreMongo("3", "Повесть");
        GenreMongo result = genreDao.save(genre);

        assertThat(result).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    @Rollback
    @DisplayName("Обновить жанр")
    void updateTest() {
        GenreMongo genre = new GenreMongo("1", "Повесть");
        GenreMongo result = genreDao.save(genre);

        assertThat(result).usingRecursiveComparison().isEqualTo(genre);
    }

    @Test
    @DisplayName("Получить жанр по ид")
    void getByIdTest() {
        Optional<GenreMongo> result = genreDao.findById("1");
        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("name", "Детектив");
    }

    @Test
    @Rollback
    @DisplayName("Ошибка удаления жанра")
    void deleteWithConstraintsByIdsExceptionTest() {
        assertThatThrownBy(() -> genreDao.deleteWithConstraintsByIds(List.of("3"))).isInstanceOf(ConstrainDeleteException.class);
    }

    @Test
    @Rollback
    @DisplayName("Удалить жанр по ид")
    void deleteWithConstraintsByIdsTest() throws ConstrainDeleteException {
        Optional<String> genreId = genreDao.findById("9").map(GenreMongo::getId);
        assertThat(genreId).isPresent();

        genreDao.deleteWithConstraintsByIds(List.of(genreId.get()));

        Optional<GenreMongo> result = genreDao.findById("9");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Получить все жанры")
    void getAllTest() {
        List<GenreMongo> result = genreDao.findAll();

        assertThat(result).asList()
                .anySatisfy(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", "5")
                        .hasFieldOrPropertyWithValue("name", "Программирование"))
                .anySatisfy(genre -> assertThat(genre).hasFieldOrPropertyWithValue("id", "2")
                        .hasFieldOrPropertyWithValue("name", "Компьютерная литература"));
    }
}
