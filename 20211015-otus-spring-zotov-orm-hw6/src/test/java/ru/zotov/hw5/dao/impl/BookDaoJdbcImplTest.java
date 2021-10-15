package ru.zotov.hw5.dao.impl;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw5.dao.mapper.BookMapper;
import ru.zotov.hw5.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Created by ZotovES on 09.10.2021
 */
@JdbcTest
@Import(BookDaoJdbcImpl.class)
@DisplayName("Тестирование репозитория книг")
class BookDaoJdbcImplTest {
    @Autowired private BookDaoJdbcImpl bookDao;

    @Test
    @DisplayName("Создание")
    void createTest() {
        Book book = Book.builder().name("Книга про тестирование").releaseYear(2021).build();
        Book result = bookDao.create(book);

        assertThat(result).isNotNull().hasFieldOrProperty("id").isNotNull()
                .usingRecursiveComparison().ignoringFields("id").isEqualTo(book);
    }

    @Test
    @DisplayName("Редактирование")
    void updateTest() {
        Book book = Book.builder().id(1L).name("Книга про тестирование").releaseYear(2021).build();
        Book result = bookDao.update(book);

        assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @Rollback
    @DisplayName("Удалить по ид")
    void deleteByIdTest() {
        bookDao.deleteById(1L);
        Optional<Book> result = bookDao.getById(1L);

        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Найти по ид")
    void getByIdTest() {
        Optional<Book> result = bookDao.getById(1L);

        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                .hasFieldOrPropertyWithValue("releaseYear", 2017);
    }

    @Test
    @DisplayName("Получить все книги")
    void findAllTest() {
        List<Book> result = bookDao.findAll();

        assertThat(result).asList().hasSize(2)
                .anySatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017)
                        .hasFieldOrPropertyWithValue("id", 1L))
                .anySatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Чистая архитектура")
                        .hasFieldOrPropertyWithValue("releaseYear", 2018)
                        .hasFieldOrPropertyWithValue("id", 2L));
    }

    @Test
    @DisplayName("Найти по наименованию")
    void findByNameTest() {
        List<Book> result = bookDao.findByName("Высоконагруженные приложения");

        assertThat(result).asList().hasSize(1)
                .allSatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017)
                        .hasFieldOrPropertyWithValue("id", 1L));
    }

    @Test
    @DisplayName("Найти по фио автора")
    void findByAuthorFioTest() {
        List<Book> result = bookDao.findByAuthorFio("Роберт Мартин");

        assertThat(result).asList().hasSize(1)
                .allSatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017)
                        .hasFieldOrPropertyWithValue("id", 1L));
    }

    @Test
    @DisplayName("Найти по наименованию жанра")
    void findByGenreNameTest() {
        List<Book> result = bookDao.findByGenreName("Компьютерная литература");

        assertThat(result).asList().hasSize(1)
                .allSatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Чистая архитектура")
                        .hasFieldOrPropertyWithValue("releaseYear", 2018)
                        .hasFieldOrPropertyWithValue("id", 2L));
    }

    @Configuration
    static class Config {
        @Bean
        BookMapper getMapper() {
            return new BookMapper();
        }
    }
}
