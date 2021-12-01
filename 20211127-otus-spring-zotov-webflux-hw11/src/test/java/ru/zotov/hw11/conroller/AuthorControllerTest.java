package ru.zotov.hw11.conroller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpMethod;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import ru.zotov.hw11.dto.AuthorDto;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * @author Created by ZotovES on 22.11.2021
 */
@EnableConfigurationProperties
@SpringBootTest
@DisplayName("Тестирование контроллера авторов")
class AuthorControllerTest {
    @Qualifier("authorsRoutes")
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
    @DisplayName("Создание автора")
    void createTest() {
        AuthorDto author = new AuthorDto(null, "test");

        client.post()
                .uri("/api/authors")
                .body(Mono.just(author), AuthorDto.class)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(AuthorDto.class)
                .value(resAuthor -> assertThat(resAuthor).isNotNull().hasFieldOrProperty("id").isNotNull()
                        .usingRecursiveComparison().ignoringFields("id").isEqualTo(author));
    }

    @Test
    @DisplayName("Получить всех авторов")
    void getAllTest() {
        AuthorDto author = new AuthorDto("3", "Билл Любанович");
        client.get()
                .uri("/api/authors")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(AuthorDto.class)
                .contains(author);
    }

    @Test
    @DisplayName("Получить автора по ид")
    void getByIdTest() {
        AuthorDto author = new AuthorDto("3", "Билл Любанович");
        client.get()
                .uri("/api/authors/{id}", 3)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDto.class)
                .isEqualTo(author);
    }

    @Test
    @DisplayName("Редактировать автора")
    void updateTest() {
        AuthorDto authorDto = new AuthorDto("2", "test");

        client.put().uri("/api/authors")
                .body(Mono.just(authorDto), AuthorDto.class)
                .exchange()
                .expectStatus().isOk()
                .expectBody(AuthorDto.class)
                .isEqualTo(authorDto);
    }

    @Test
    @DisplayName("Удалить авторов по списку ид")
    void deleteByListIdsTest() throws Exception {
        client.get()
                .uri("/api/authors/{id}", 10)
                .exchange()
                .expectStatus().isOk();


        client.method(HttpMethod.DELETE)
                .uri("/api/authors")
                .body(Mono.just(List.of("10")), List.class)
                .exchange()
                .expectStatus().isOk();

        client.get()
                .uri("/api/authors/{id}", 10)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    @DisplayName("Ошибка при удалении связанного с книгой автора")
    void deleteByListIdsExceptionTest() {
        client.method(HttpMethod.DELETE).uri("/api/authors")
                .body(Flux.just("1"), List.class)
                .exchange()
                .expectStatus().isBadRequest();
    }
}
