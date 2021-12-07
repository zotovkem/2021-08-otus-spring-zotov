package ru.zotov.hw11.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.zotov.hw11.dao.AuthorRepository;
import ru.zotov.hw11.dao.BookRepository;
import ru.zotov.hw11.dao.GenreRepository;
import ru.zotov.hw11.domain.Author;
import ru.zotov.hw11.domain.Genre;
import ru.zotov.hw11.dto.AuthorDto;
import ru.zotov.hw11.dto.GenreDto;
import ru.zotov.hw11.handler.BookHandler;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

/**
 * @author Created by ZotovES on 27.11.2021
 * Конфигурация роутов
 */
@Configuration
public class RouteConfig {
    protected static final String AUTHORS_API_URL = "/api/authors";
    protected static final String GENRES_API_URL = "/api/genres";
    protected static final String BOOKS_API_URL = "/api/books";

    /**
     * Роуты управления авторами
     *
     * @param repository репозиторий авторов
     * @param mapper     маппер
     * @return роуты
     */
    @Bean
    public RouterFunction<ServerResponse> authorsRoutes(AuthorRepository repository, ModelMapper mapper) {
        return route()
                //Получить всех авторов 
                .GET(AUTHORS_API_URL, accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(repository.findAll()
                                        .map(author -> mapper.map(author, AuthorDto.class)), AuthorDto.class))
                //Создать автора
                .POST(AUTHORS_API_URL, accept(APPLICATION_JSON),
                        request -> request.bodyToMono(AuthorDto.class)
                                .map(authorDto -> new Author(null, authorDto.getFio()))
                                .flatMap(repository::save)
                                .map(author -> mapper.map(author, AuthorDto.class))
                                .flatMap(authorDto -> ServerResponse.status(HttpStatus.CREATED)
                                        .contentType(APPLICATION_JSON)
                                        .body(fromValue(authorDto)))
                                .switchIfEmpty(badRequest().build()))
                //Редактировать автора
                .PUT(AUTHORS_API_URL, accept(APPLICATION_JSON),
                        request -> request.bodyToMono(AuthorDto.class)
                                .map(authorDto -> mapper.map(authorDto, Author.class))
                                .flatMap(repository::save)
                                .map(author -> mapper.map(author, AuthorDto.class))
                                .flatMap(authorDto -> ok().contentType(APPLICATION_JSON).body(fromValue(authorDto)))
                                .switchIfEmpty(badRequest().build()))
                //Получить автора по ид
                .GET(AUTHORS_API_URL + "/{id}", accept(APPLICATION_JSON),
                        request -> repository.findById(request.pathVariable("id"))
                                .map(author -> mapper.map(author, AuthorDto.class))
                                .flatMap(authorDto -> ok().contentType(APPLICATION_JSON).body(fromValue(authorDto)))
                                .switchIfEmpty(notFound().build()))
                //Удалить авторов по списку ид
                .DELETE(AUTHORS_API_URL, accept(APPLICATION_JSON),
                        request -> request.bodyToMono(List.class)
                                .flatMap(repository::deleteWithConstraintsByIds)
                                .flatMap(v -> noContent().build())
                                .onErrorResume(error -> badRequest().build()))
                .build();
    }

    /**
     * Роуты управления жанрами
     *
     * @param repository репозиторий жанров
     * @param mapper     маппер
     * @return роуты
     */
    @Bean
    public RouterFunction<ServerResponse> genresRoutes(GenreRepository repository, ModelMapper mapper) {
        return route()
                //Получить все жанры
                .GET(GENRES_API_URL, accept(APPLICATION_JSON),
                        request -> ok().contentType(APPLICATION_JSON)
                                .body(repository.findAll()
                                        .map(genre -> mapper.map(genre, GenreDto.class)), GenreDto.class))
                //Создать жанр
                .POST(GENRES_API_URL, accept(APPLICATION_JSON),
                        request -> request.bodyToMono(GenreDto.class)
                                .map(genreDto -> new Genre(null, genreDto.getName()))
                                .flatMap(repository::save)
                                .map(genre -> mapper.map(genre, GenreDto.class))
                                .flatMap(genreDto -> ServerResponse.status(HttpStatus.CREATED)
                                        .contentType(APPLICATION_JSON)
                                        .body(fromValue(genreDto)))
                                .switchIfEmpty(badRequest().build()))
                //Удалить жанр
                .DELETE(GENRES_API_URL, accept(APPLICATION_JSON),
                        request -> request.bodyToMono(List.class)
                                .flatMap(repository::deleteWithConstraintsByIds)
                                .flatMap(v -> noContent().build())
                                .onErrorResume(error -> badRequest().build()))
                //Редактировать жанр
                .PUT(GENRES_API_URL, accept(APPLICATION_JSON),
                        request -> request.bodyToMono(GenreDto.class)
                                .map(genreDto -> mapper.map(genreDto, Genre.class))
                                .flatMap(repository::save)
                                .map(genre -> mapper.map(genre, GenreDto.class))
                                .flatMap(genreDto -> ok().contentType(APPLICATION_JSON).body(fromValue(genreDto)))
                                .switchIfEmpty(badRequest().build()))
                //Получить жанр по ид
                .GET(GENRES_API_URL + "/{id}", accept(APPLICATION_JSON),
                        request -> repository.findById(request.pathVariable("id"))
                                .map(genre -> mapper.map(genre, GenreDto.class))
                                .flatMap(genreDto -> ok().contentType(APPLICATION_JSON).body(fromValue(genreDto)))
                                .switchIfEmpty(notFound().build()))
                .build();
    }

    /**
     * Роуты управления книгами
     *
     * @param bookRepository репозиторий книг
     * @param mapper         маппер
     * @return роуты
     */
    @Bean
    public RouterFunction<ServerResponse> booksRoutes(BookRepository bookRepository, AuthorRepository authorRepository,
            GenreRepository genreRepository, ModelMapper mapper) {
        BookHandler bookHandler = new BookHandler(bookRepository, authorRepository, genreRepository, mapper);
        return route()
                //Получить все книги
                .GET(BOOKS_API_URL, accept(APPLICATION_JSON), bookHandler.findByAll())
                //Создать книгу
                .POST(BOOKS_API_URL, accept(APPLICATION_JSON), bookHandler.create())
                //Удалить книги по списку ид
                .DELETE(BOOKS_API_URL, accept(APPLICATION_JSON), bookHandler.deleteByIds())
                //Редактировать книгу
                .PUT(BOOKS_API_URL, accept(APPLICATION_JSON), bookHandler.update())
                //Получить книгу по ид
                .GET(BOOKS_API_URL + "/{id}", accept(APPLICATION_JSON), bookHandler.findById())
                .build();
    }
}
