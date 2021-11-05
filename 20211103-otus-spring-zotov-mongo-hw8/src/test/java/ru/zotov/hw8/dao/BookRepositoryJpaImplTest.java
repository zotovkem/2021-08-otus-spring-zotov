package ru.zotov.hw8.dao;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import ru.zotov.hw8.domain.Book;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Created by ZotovES on 09.10.2021
 */
@DataJpaTest
@DisplayName("Тестирование репозитория книг")
class BookRepositoryJpaImplTest {
    @Autowired private BookRepository bookDao;
    @Autowired private TestEntityManager em;

    SessionFactory sessionFactory;

    @SuppressWarnings("unused")
    @BeforeEach
    private void getSessionFactory() {
        sessionFactory = em.getEntityManager().getEntityManagerFactory()
                .unwrap(SessionFactory.class);
        sessionFactory.getStatistics().setStatisticsEnabled(true);
    }

    @SuppressWarnings("unused")
    @AfterEach
    private void clearSessionStatistic() {
        sessionFactory.getStatistics().clear();
    }

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
        Book book = Book.builder().id(1L).name("Книга про тестирование").releaseYear(2021).build();
        Book result = bookDao.save(book);

        assertThat(result).isNotNull().usingRecursiveComparison().isEqualTo(book);
    }

    @Test
    @Rollback
    @DisplayName("Удалить по ид")
    void deleteByIdTest() {
        Optional<Book> book = bookDao.findById(1L);
        assertThat(book).isPresent();

        bookDao.delete(book.get());

        Optional<Book> result = bookDao.findById(1L);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Найти по ид")
    void getByIdTest() {
        Optional<Book> result = bookDao.findById(1L);

        assertThat(result).isPresent().get().hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                .hasFieldOrPropertyWithValue("releaseYear", 2017);
    }

    @Test
    @DisplayName("Получить все книги")
    void findAllTest() {
        List<Book> result = bookDao.findAll();

        assertThat(result).isNotNull().hasSize(2)
                .allMatch(book -> book.getGenres() != null && !book.getGenres().isEmpty())
                .allMatch(book -> book.getAuthors() != null && !book.getGenres().isEmpty())
                .anySatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017)
                        .hasFieldOrPropertyWithValue("id", 1L)
                        .extracting("comments").asList().isNotEmpty())
                .anySatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Чистая архитектура")
                        .hasFieldOrPropertyWithValue("releaseYear", 2018)
                        .hasFieldOrPropertyWithValue("id", 2L)
                        .extracting("comments").asList().isNotEmpty());

        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(4);
    }

    @Test
    @DisplayName("Найти по наименованию")
    void findByNameTest() {
        List<Book> result = bookDao.findByName("Высоконагруженные приложения");

        assertThat(result).asList().hasSize(1)
                .allSatisfy(book -> assertThat(book).hasFieldOrPropertyWithValue("name", "Высоконагруженные приложения")
                        .hasFieldOrPropertyWithValue("releaseYear", 2017)
                        .hasFieldOrPropertyWithValue("id", 1L));
        assertThat(sessionFactory.getStatistics().getPrepareStatementCount()).isEqualTo(1);
    }
}
