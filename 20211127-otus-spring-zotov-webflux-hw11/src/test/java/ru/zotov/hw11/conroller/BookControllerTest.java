package ru.zotov.hw11.conroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.zotov.hw11.Hw11Application;
import ru.zotov.hw11.dto.AuthorDto;
import ru.zotov.hw11.dto.BookDto;
import ru.zotov.hw11.dto.CommentDto;
import ru.zotov.hw11.dto.GenreDto;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Created by ZotovES on 22.11.2021
 */
@EnableConfigurationProperties
@SpringBootTest(classes = Hw11Application.class)
@AutoConfigureMockMvc
@DisplayName("Тестирование контроллера книг")
class BookControllerTest {
    @Qualifier("booksRoutes")
    @Autowired
    private RouterFunction<ServerResponse> route;
    private static WebTestClient client;

    @BeforeEach
    void setUp() {
        if (client == null) {
            client = WebTestClient
                    .bindToRouterFunction(route)
                    .build();
        }
    }

    @Test
    @DisplayName("Создание книги")
    void createBookTest() {
        BookDto bookDto = BookDto.builder()
                .authors(Set.of(new AuthorDto("1", "Роберт Мартин")))
                .genres(Set.of(new GenreDto("1", "Детектив")))
                .comments(Collections.emptyList())
                .name("test")
                .releaseYear(2010)
                .build();

        client.post().uri("/api/books")
                .body(Mono.just(bookDto), BookDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(BookDto.class)
                .value(resBook -> assertThat(resBook).isNotNull().hasFieldOrProperty("id").isNotNull()
                        .usingRecursiveComparison().ignoringFields("id").isEqualTo(bookDto));
    }

    @Test
    @DisplayName("Получить все книги")
    void bookGetAllTest() {
        BookDto bookDto = BookDto.builder()
                .id("2")
                .name("Чистая архитектура")
                .releaseYear(2018)
                .authors(Set.of(new AuthorDto("1", "Роберт Мартин"), new AuthorDto("7", "Александр Киселев")))
                .genres(Set.of(new GenreDto("2", null), new GenreDto("7", null)))
                .comments(List.of(new CommentDto("2", "Хорошая книга", "ЗотовЕС",
                                new Date(ZonedDateTime.parse("2020-02-01T19:10:25+07:00").toEpochSecond())),
                        new CommentDto("8", "Тестовый комментарий", "Петров",
                                new Date(ZonedDateTime.parse("2020-02-01T19:10:25+07:00").toEpochSecond()))))
                .build();

        client.get().uri("/api/books")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(BookDto.class)
                .contains(bookDto);
    }

    @Test
    @DisplayName("Получить книгу по ид")
    void getByIdTest() {
        BookDto bookDto = BookDto.builder()
                .id("2")
                .name("Чистая архитектура")
                .releaseYear(2018)
                .authors(Set.of(new AuthorDto("1", "Роберт Мартин"), new AuthorDto("7", "Александр Киселев")))
                .genres(Set.of(new GenreDto("2", null), new GenreDto("7", null)))
                .comments(List.of(new CommentDto("2", "Хорошая книга", "ЗотовЕС",
                                new Date(ZonedDateTime.parse("2020-02-01T19:10:25+07:00").toEpochSecond())),
                        new CommentDto("8", "Тестовый комментарий", "Петров",
                                new Date(ZonedDateTime.parse("2020-02-01T19:10:25+07:00").toEpochSecond()))))
                .build();

        client.get().uri("/api/books/{id}", 2)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .isEqualTo(bookDto);
    }

    @Test
    @DisplayName("Редактировать книгу")
    void updateBookTest() {
        BookDto bookDto = BookDto.builder()
                .id("1")
                .authors(Set.of(new AuthorDto("1", "Роберт Мартин")))
                .genres(Set.of(new GenreDto("1", "Детектив")))
                .name("test")
                .releaseYear(2010)
                .build();

        client.put().uri("/api/books")
                .body(Mono.just(bookDto), BookDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(BookDto.class)
                .value(resBook -> assertThat(resBook).hasFieldOrPropertyWithValue("id", bookDto.getId())
                        .hasFieldOrPropertyWithValue("name", bookDto.getName())
                        .hasFieldOrPropertyWithValue("releaseYear", bookDto.getReleaseYear()));
    }

    @Test
    @DisplayName("Удалить книги по списку ид")
    void deleteByIdTest() {
        client.get().uri("/api/books/{id}", 1)
                .exchange()
                .expectStatus().isOk();

        client.method(HttpMethod.DELETE).uri("/api/books")
                .body(Flux.just("1"), BookDto.class)
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/api/books/{id}", 1)
                .exchange()
                .expectStatus().isNotFound();
    }
}
