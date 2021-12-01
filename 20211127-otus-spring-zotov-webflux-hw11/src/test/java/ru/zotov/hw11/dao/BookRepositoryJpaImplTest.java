package ru.zotov.hw11.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import ru.zotov.hw11.domain.Book;

import java.util.Comparator;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan(value = {"ru.zotov.hw11.dao"})
@DisplayName("Тестирование репозитория книг")
class BookRepositoryJpaImplTest {
    @Autowired private BookRepository bookRepository;

    @Test
    @DisplayName("Создание")
    void createTest() {
        Book book = Book.builder().name("Книга про тестирование").releaseYear(2021).build();
        Mono<Book> result = bookRepository.save(book);

        StepVerifier.create(result)
                .assertNext(resBook -> assertThat(resBook).isNotNull().hasFieldOrProperty("id").isNotNull()
                        .usingRecursiveComparison().ignoringFields("id").isEqualTo(book))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Редактирование")
    void updateTest() {
        Book book = Book.builder().id("1").name("Книга про тестирование").releaseYear(2021).build();
        Mono<Book> result = bookRepository.save(book);

        StepVerifier.create(result)
                .assertNext(resBook -> assertThat(resBook).isNotNull().usingRecursiveComparison().isEqualTo(book))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Удалить по ид")
    void deleteByIdTest() {
        String bookId = "1";
        Mono<Book> book = bookRepository.findById(bookId);
        StepVerifier.create(book)
                .assertNext(Assertions::assertNotNull)
                .expectComplete()
                .verify();

        StepVerifier.create(bookRepository.deleteById(bookId))
                .expectNextCount(0)
                .verifyComplete();

        Mono<Book> result = bookRepository.findById(bookId);
        StepVerifier.create(result)
                .expectNextCount(0)
                .verifyComplete();
    }

    @Test
    @DisplayName("Найти по ид")
    void getByIdTest() {
        Mono<Book> result = bookRepository.findById("1");

        StepVerifier.create(result)
                .assertNext(resBook -> assertThat(resBook).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017));
    }

    @Test
    @DisplayName("Получить все книги")
    void findAllTest() {
        Flux<Book> result = bookRepository.findAll();

        StepVerifier.create(
                        result.filter(b -> "2".equals(b.getId()) || "6".equals(b.getId())).sort(Comparator.comparing(Book::getId)))
                .assertNext(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Чистая архитектура")
                        .hasFieldOrPropertyWithValue("releaseYear", 2018)
                        .hasFieldOrPropertyWithValue("id", "2"))
                .assertNext(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Отдаленные последствия")
                        .hasFieldOrPropertyWithValue("releaseYear", 2021)
                        .hasFieldOrPropertyWithValue("id", "6"))
                .expectComplete()
                .verify();
    }

    @Test
    @DisplayName("Найти по наименованию")
    void findByNameTest() {
        Flux<Book> result = bookRepository.findByName("Высоконагруженные приложения");

        StepVerifier.create(result)
                .assertNext(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017)
                        .hasFieldOrPropertyWithValue("id", "1"))
                .expectNextCount(0)
                .expectComplete()
                .verify();
    }
}
