package ru.zotov.hw8.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw8.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan(value = {"ru.zotov.hw8.converters", "ru.zotov.hw8.dao"})
@DisplayName("Тестирование репозитория книг")
class BookRepositoryJpaImplTest {
    @Autowired private BookRepository bookDao;

    @Test
    @DisplayName("Создание")
    void createTest() {
        Book book = Book.builder().name("Книга про тестирование").releaseYear(2021).build();
        Book result = bookDao.save(book);

        assertThat(result).isNotNull().hasFieldOrProperty("id").isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(book);
    }

    @Test
    @DisplayName("Редактирование")
    void updateTest() {
        Book book = Book.builder().id("1").name("Книга про тестирование").releaseYear(2021).build();
        Book result = bookDao.save(book);

        assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @Rollback
    @DisplayName("Удалить по ид")
    void deleteByIdTest() {
        Optional<String> book = bookDao.findById("1").map(Book::getId);
        assertThat(book).isPresent();

        bookDao.deleteById(book.get());

        Optional<Book> result = bookDao.findById("1");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Найти по ид")
    void getByIdTest() {
        Optional<Book> result = bookDao.findById("1");

        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                .hasFieldOrPropertyWithValue("releaseYear", 2017);
    }

    @Test
    @DisplayName("Получить все книги")
    void findAllTest() {
        List<Book> result = bookDao.findAll();

        assertThat(result).isNotNull()
                .anySatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Отдаленные последствия")
                        .hasFieldOrPropertyWithValue("releaseYear", 2021)
                        .hasFieldOrPropertyWithValue("id", "6"))
                .anySatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Чистая архитектура")
                        .hasFieldOrPropertyWithValue("releaseYear", 2018)
                        .hasFieldOrPropertyWithValue("id", "2"));
    }

    @Test
    @DisplayName("Найти по наименованию")
    void findByNameTest() {
        List<Book> result = bookDao.findByName("Высоконагруженные приложения");

        assertThat(result).asList().hasSize(1)
                .allSatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017)
                        .hasFieldOrPropertyWithValue("id", "1"));
    }
}
