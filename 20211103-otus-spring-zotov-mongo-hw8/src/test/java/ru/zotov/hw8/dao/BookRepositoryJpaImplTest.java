package ru.zotov.hw8.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Comment;

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
    @Autowired private BookRepository bookRepository;
    @Autowired private CommentRepository commentRepository;

    @Test
    @DisplayName("Создание")
    void createTest() {
        Book book = Book.builder().name("Книга про тестирование").releaseYear(2021).build();
        Book result = bookRepository.save(book);

        assertThat(result).isNotNull().hasFieldOrProperty("id").isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(book);
    }

    @Test
    @DisplayName("Редактирование")
    void updateTest() {
        Book book = Book.builder().id("1").name("Книга про тестирование").releaseYear(2021).build();
        Book result = bookRepository.save(book);

        assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @DisplayName("Удалить по ид")
    void deleteByIdTest() {
        Optional<String> bookId = bookRepository.findById("1").map(Book::getId);
        assertThat(bookId).isPresent();

        bookRepository.deleteById(bookId.get());

        Optional<Book> result = bookRepository.findById("1");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Каскадное удаление комментариев по ид книги ")
    void cascadeDeleteByIdTest() {
        Optional<String> bookId = bookRepository.findById("3").map(Book::getId);
        assertThat(bookId).isPresent();
        List<Comment> comments = commentRepository.findByBookId("3");
        assertThat(comments).isNotEmpty();

        bookRepository.cascadeDeleteById(bookId.get());

        Optional<Book> result = bookRepository.findById("3");
        assertThat(result).isEmpty();
        comments = commentRepository.findByBookId("3");
        assertThat(comments).isEmpty();
    }

    @Test
    @DisplayName("Найти по ид")
    void getByIdTest() {
        Optional<Book> result = bookRepository.findById("1");

        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                .hasFieldOrPropertyWithValue("releaseYear", 2017);
    }

    @Test
    @DisplayName("Получить все книги")
    void findAllTest() {
        List<Book> result = bookRepository.findAll();

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
        List<Book> result = bookRepository.findByName("Высоконагруженные приложения");

        assertThat(result).asList().hasSize(1)
                .allSatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017)
                        .hasFieldOrPropertyWithValue("id", "1"));
    }
}
