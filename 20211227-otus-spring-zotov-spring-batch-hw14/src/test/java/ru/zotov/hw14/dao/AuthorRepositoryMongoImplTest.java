package ru.zotov.hw14.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw14.domain.AuthorMongo;
import ru.zotov.hw14.exception.ConstrainDeleteException;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan(value = {"ru.zotov.hw14.converters", "ru.zotov.hw14.dao"})
@DisplayName("Тестирование репозитория авторов")
class AuthorRepositoryMongoImplTest {
    @Autowired private AuthorRepositoryMongo authorDao;

    @Test
    @DisplayName("Создать автора")
    void createTest() {
        AuthorMongo author = new AuthorMongo(null, "Иванов");
        AuthorMongo result = authorDao.save(author);

        assertThat(result).isNotNull().hasFieldOrProperty("id").isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(author);
    }

    @Test
    @Rollback
    @DisplayName("Обновить автора")
    void updateTest() {
        AuthorMongo author = new AuthorMongo("1", "Иванов");
        AuthorMongo result = authorDao.save(author);

        assertThat(result).usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    @DisplayName("Получить автора по ид")
    void getByIdTest() {
        Optional<AuthorMongo> result = authorDao.findById("2");
        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("fio", "Мартин Клеппман");
    }

    @Test
    @Rollback
    @DisplayName("Удалить автора")
    void deleteWithConstraintsByIdsTest() throws ConstrainDeleteException {
        Optional<AuthorMongo> author = authorDao.findById("9");
        assertThat(author).isPresent();

        authorDao.deleteWithConstraintsByIds(List.of("9"));

        Optional<AuthorMongo> result = authorDao.findById("9");
        assertThat(result).isEmpty();
    }

    @Test
    @Rollback
    @DisplayName("Ошибка удаления автора")
    void deleteWithConstraintsByIdsExceptionTest() {
        assertThatThrownBy(() -> authorDao.deleteWithConstraintsByIds(List.of("3"))).isInstanceOf(ConstrainDeleteException.class);
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findByAllTest() {
        List<AuthorMongo> result = authorDao.findAll();

        assertThat(result).asList()
                .anySatisfy(author ->
                        assertThat(author).hasFieldOrPropertyWithValue("id", "7")
                                .hasFieldOrPropertyWithValue("fio", "Александр Киселев"))
                .anySatisfy(author ->
                        assertThat(author).hasFieldOrPropertyWithValue("id", "4")
                                .hasFieldOrPropertyWithValue("fio", "Александр Сергеевич Пушкин"));
    }

    @Test
    @DisplayName("Найти по фио автора")
    void findByAuthorFioTest() {
        List<AuthorMongo> result = authorDao.findByFio("Роберт Мартин");

        Assertions.assertThat(result).asList().hasSize(1)
                .allSatisfy(book -> Assertions.assertThat(book).hasFieldOrPropertyWithValue("fio", "Роберт Мартин"));
    }
}
