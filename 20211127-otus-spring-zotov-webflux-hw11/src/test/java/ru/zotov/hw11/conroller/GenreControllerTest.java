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
import ru.zotov.hw11.dto.GenreDto;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Created by ZotovES on 22.11.2021
 */
@EnableConfigurationProperties
@SpringBootTest(classes = Hw11Application.class)
@AutoConfigureMockMvc
@DisplayName("Тестирование контроллера жанров")
class GenreControllerTest {
    @Qualifier("genresRoutes")
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
    @DisplayName("Добавление жанра")
    void addGenreTest() {
        GenreDto genreDto = new GenreDto(null, "test");
        client.post().uri("/api/genres")
                .body(Mono.just(genreDto), GenreDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(GenreDto.class)
                .value(resGenre -> assertThat(resGenre).usingRecursiveComparison().ignoringFields("id").isEqualTo(genreDto));
    }

    @Test
    @DisplayName("Получить жанр по ид")
    void getByIdTest() {
        GenreDto genreDto = new GenreDto("5", "Программирование");
        client.get().uri("/api/genres/{id}", genreDto.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenreDto.class)
                .isEqualTo(genreDto);
    }

    @Test
    @DisplayName("Получить все жанры")
    void getAllTest() {
        client.get().uri("/api/genres")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(GenreDto.class)
                .contains(new GenreDto("5", "Программирование"), new GenreDto("2", "Компьютерная литература"));
    }

    @Test
    @DisplayName("Редактировать жанр")
    void updateTest() {
        GenreDto genreDto = new GenreDto("1", "test");
        client.put().uri("/api/genres")
                .body(Mono.just(genreDto), GenreDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(GenreDto.class)
                .isEqualTo(genreDto);
    }

    @Test
    @DisplayName("Удалить жанры по списку ид")
    void deleteByListIdsTest() {
        client.get().uri("/api/genres/{id}", 10)
                .exchange()
                .expectStatus().isOk();

        client.method(HttpMethod.DELETE).uri("/api/genres")
                .body(Flux.just("10"), List.class)
                .exchange()
                .expectStatus().isOk();

        client.get().uri("/api/genres/{id}", 10)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Ошибка при удалении связанного с книгой жанра")
    void deleteByListIdsExceptionTest() {
        client.method(HttpMethod.DELETE).uri("/api/genres")
                .body(Flux.just("1"), List.class)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
