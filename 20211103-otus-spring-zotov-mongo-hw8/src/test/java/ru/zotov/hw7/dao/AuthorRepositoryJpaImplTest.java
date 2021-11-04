package ru.zotov.hw7.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw7.domain.Author;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataJpaTest
@DisplayName("Тестирование репозитория авторов")
class AuthorRepositoryJpaImplTest {
    @Autowired private AuthorRepository authorDao;

    @Test
    @DisplayName("Создать автора")
    void createTest() {
        Author author = new Author(null, "Иванов", Collections.emptyList());
        Author result = authorDao.save(author);

        assertThat(result).isNotNull().hasFieldOrProperty("id").isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(author);
    }

    @Test
    @DisplayName("Обновить автора")
    void updateTest() {
        Author author = new Author(1L, "Иванов", null);
        Author result = authorDao.save(author);

        assertThat(result).usingRecursiveComparison().isEqualTo(author);
    }

    @Test
    @DisplayName("Получить автора по ид")
    void getByIdTest() {
        Optional<Author> result = authorDao.findById(2L);
        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("fio", "Александр Сергеевич Пушкин");
    }

    @Test
    @Rollback
    @DisplayName("Удалить автора")
    void deleteByIdTest() {
        Optional<Author> author = authorDao.findById(3L);
        assertThat(author).isPresent();

        authorDao.delete(author.get());

        Optional<Author> result = authorDao.findById(3L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findByAllTest() {
        List<Author> result = authorDao.findAll();

        assertThat(result).asList().hasSize(3)
                .anySatisfy(author ->
                        assertThat(author).hasFieldOrPropertyWithValue("id", 1L)
                                .hasFieldOrPropertyWithValue("fio", "Роберт Мартин"))
                .anySatisfy(author ->
                        assertThat(author).hasFieldOrPropertyWithValue("id", 2L)
                                .hasFieldOrPropertyWithValue("fio", "Александр Сергеевич Пушкин"));
    }

    @Test
    @DisplayName("Найти по фио автора")
    void findByAuthorFioTest() {
        List<Author> result = authorDao.findByFio("Роберт Мартин");

        Assertions.assertThat(result).asList().hasSize(1)
                .allSatisfy(book -> Assertions.assertThat(book).hasFieldOrPropertyWithValue("fio", "Роберт Мартин"));
    }
}
