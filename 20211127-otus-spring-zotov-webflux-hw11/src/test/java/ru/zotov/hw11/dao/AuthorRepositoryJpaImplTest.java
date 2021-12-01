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
import ru.zotov.hw11.domain.Author;
import ru.zotov.hw11.exception.ConstrainDeleteException;

import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan(value = {"ru.zotov.hw11.dao"})
@DisplayName("Тестирование репозитория авторов")
class AuthorRepositoryJpaImplTest {
    @Autowired private AuthorRepository authorDao;

    @Test
    @DisplayName("Создать автора")
    void createTest() {
        Author author = new Author(null, "Иванов");
        Mono<Author> result = authorDao.save(author);

        StepVerifier
                .create(result)
                .assertNext(resAuthor -> assertThat(resAuthor).isNotNull().hasFieldOrProperty("id").isNotNull()
                        .usingRecursiveComparison().ignoringFields("id").isEqualTo(author))
                .expectComplete()
                .verify();
    }

    @Test
    @Rollback
    @DisplayName("Обновить автора")
    void updateTest() {
        Author author = new Author("1", "Иванов");
        Mono<Author> result = authorDao.save(author);

        StepVerifier
                .create(result)
                .assertNext(resAuthor -> assertThat(resAuthor).usingRecursiveComparison().isEqualTo(author))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Получить автора по ид")
    void getByIdTest() {
        Mono<Author> result = authorDao.findById("2");
        StepVerifier
                .create(result)
                .assertNext(resAuthor -> assertThat(resAuthor).hasFieldOrPropertyWithValue("fio", "Мартин Клеппман"))
                .expectComplete()
                .verify();
    }

    @Test
    @Rollback
    @DisplayName("Удалить автора")
    void deleteWithConstraintsByIdsTest() throws ConstrainDeleteException {
        Mono<Author> author = authorDao.findById("9");
        StepVerifier
                .create(author)
                .assertNext(Assertions::assertNotNull)
                .expectComplete()
                .verify();

        StepVerifier.create(authorDao.deleteWithConstraintsByIds(List.of("9")))
                .expectNextCount(0)
                .verifyComplete();

        Mono<Author> result = authorDao.findById("9");
        StepVerifier
                .create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @Rollback
    @DisplayName("Ошибка удаления автора")
    void deleteWithConstraintsByIdsExceptionTest() {
        Mono<Void> result = authorDao.deleteWithConstraintsByIds(List.of("3"));
        StepVerifier
                .create(result)
                .expectError(ConstrainDeleteException.class)
                .verify();
    }

    @Test
    @DisplayName("Получить всех авторов")
    void findByAllTest() {
        Flux<Author> result = authorDao.findAll();
        StepVerifier
                .create(result.filter(a -> a.getId().equals("4") || a.getId().equals("7"))
                        .sort(Comparator.comparing(Author::getId)))
                .assertNext(resAuthor -> assertThat(resAuthor).hasFieldOrPropertyWithValue("id", "4")
                        .hasFieldOrPropertyWithValue("fio", "Александр Сергеевич Пушкин"))
                .assertNext(resAuthor -> assertThat(resAuthor).hasFieldOrPropertyWithValue("id", "7")
                        .hasFieldOrPropertyWithValue("fio", "Александр Киселев"))
                .expectComplete()
                .verify();
    }
}
