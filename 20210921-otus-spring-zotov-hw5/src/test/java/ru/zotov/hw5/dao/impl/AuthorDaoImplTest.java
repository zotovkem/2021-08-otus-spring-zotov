package ru.zotov.hw5.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw5.dao.mapper.AuthorMapper;
import ru.zotov.hw5.domain.Author;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Created by ZotovES on 09.10.2021
 */
@DisplayName("Тестирование репозитория авторов")
@JdbcTest
@Import(AuthorDaoJdbcImpl.class)
class AuthorDaoImplTest {
    @Autowired private AuthorDaoJdbcImpl authorDao;

    @Test
    @DisplayName("Создать автора")
    void createTest() {
        Author author = new Author(3L, "Иванов");
        authorDao.create(author);

        Optional<Author> result = authorDao.getById(3L);
        assertThat(result).isPresent().get().usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    @DisplayName("Обновить автора")
    void updateTest() {
        Author author = new Author(1L, "Иванов");
        authorDao.update(author);

        Optional<Author> result = authorDao.getById(1L);
        assertThat(result).isPresent().get().usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    @DisplayName("Получить автора по ид")
    void getByIdTest() {
        Optional<Author> result = authorDao.getById(2L);
        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("fio", "Александр Сергеевич Пушкин");
    }

    @Test
    @Rollback
    @DisplayName("Удалить автора")
    void deleteByIdTest() {
        authorDao.deleteById(3L);

        Optional<Author> result = authorDao.getById(3L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Получить по списку ид")
    void findByIdsInTest() {
        List<Author> result = authorDao.findByIdsIn(List.of(1L));

        assertThat(result).asList().hasSize(1)
                .allSatisfy(author -> assertThat(author).hasFieldOrPropertyWithValue("id", 1L)
                        .hasFieldOrPropertyWithValue("fio", "Роберт Мартин"));
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findByAllTest() {
        List<Author> result = authorDao.findByAll();

        assertThat(result).asList().hasSize(2)
                .anySatisfy(author ->
                        assertThat(author).hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("fio", "Роберт Мартин"))
                .anySatisfy(author ->
                        assertThat(author).hasFieldOrPropertyWithValue("id", 2L)
                                .hasFieldOrPropertyWithValue("fio", "Александр Сергеевич Пушкин"));
    }

    @Test
    @DisplayName("Поиск по списку ид книг")
    void findByBookIdsTest() {
        List<Author> result = authorDao.findByIdsIn(List.of(2L));

        assertThat(result).asList().hasSize(1)
                .allSatisfy(author ->
                        assertThat(author).hasFieldOrPropertyWithValue("id", 2L)
                                .hasFieldOrPropertyWithValue("fio", "Александр Сергеевич Пушкин"));
    }

    @Configuration
    static class Config {
        @Bean
        AuthorMapper getMapper() {
            return new AuthorMapper();
        }
    }
}
