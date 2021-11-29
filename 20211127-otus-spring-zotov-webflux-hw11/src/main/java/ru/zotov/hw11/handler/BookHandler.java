package ru.zotov.hw11.handler;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ru.zotov.hw11.dao.AuthorRepository;
import ru.zotov.hw11.dao.BookRepository;
import ru.zotov.hw11.dao.GenreRepository;
import ru.zotov.hw11.domain.Author;
import ru.zotov.hw11.domain.Book;
import ru.zotov.hw11.domain.Comment;
import ru.zotov.hw11.domain.Genre;
import ru.zotov.hw11.dto.BookDto;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.*;

/**
 * @author Created by ZotovES on 07.10.2021
 * Реализация сервиса управления книгами
 */
@Service
@RequiredArgsConstructor
public class BookHandler {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final GenreRepository genreRepository;
    private final ModelMapper mapper;

    /**
     * Сохранить книгу
     *
     * @return книга
     */
    public HandlerFunction<ServerResponse> save() {
        return request -> ok().contentType(APPLICATION_JSON).body(saveBook(request), BookDto.class);
    }

    /**
     * Удалить книгу по ид
     */
    public HandlerFunction<ServerResponse> deleteByIds() {
        return request -> request.bodyToMono(List.class)
                .map(bookRepository::deleteByIds)
                .flatMap(bookDto -> noContent().build());
    }

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    public HandlerFunction<ServerResponse> findByAll() {
        return request -> ok().contentType(APPLICATION_JSON)
                .body(bookRepository.findAll()
                        .map(book -> mapper.map(book, BookDto.class)), BookDto.class);
    }

    /**
     * Найти книгу по ид
     *
     * @return книга
     */
    @NonNull
    public HandlerFunction<ServerResponse> findById() {
        return request -> bookRepository.findById(request.pathVariable("id"))
                .map(book -> mapper.map(book, BookDto.class))
                .flatMap(bookDto -> ok().contentType(APPLICATION_JSON).body(fromValue(bookDto)))
                .switchIfEmpty(notFound().build());
    }

    /**
     * Сохранить книгу
     *
     * @param request запрос
     * @return сохраненная книга
     */
    private Mono<BookDto> saveBook(ServerRequest request) {
        return request.bodyToMono(BookDto.class)
                .map(book -> mapper.map(book, Book.class))
                .flatMap(setAuthorsFunction())
                .flatMap(setGenresFunction())
                .map(setCommentsFunction())
                .flatMap(bookRepository::save)
                .map(book -> mapper.map(book, BookDto.class));
    }

    /**
     * Сохраняем комментарии
     */
    private Function<Book, Book> setCommentsFunction() {
        return book -> {
            List<Comment> comments = Optional.ofNullable(book.getComments()).orElse(Collections.emptyList());
            comments.forEach(comment -> {
                if (comment.getId() == null) {
                    comment.setId(UUID.randomUUID().toString());
                }
                if (comment.getCreateDate() == null) {
                    comment.setCreateDate(new Date());
                }
            });
            book.setComments(comments);
            return book;
        };
    }

    /**
     * Сохраняем авторов книги
     */
    private Function<Book, Mono<? extends Book>> setAuthorsFunction() {
        return book -> {
            List<String> authorIds = book.getAuthors().stream()
                    .map(Author::getId)
                    .collect(Collectors.toList());
            return authorRepository.findByIdIn(authorIds).collectList()
                    .map(authors -> {
                        book.setAuthors(authors);
                        return book;
                    });
        };
    }

    /**
     * Сохраняем жанры книги
     */
    private Function<Book, Mono<? extends Book>> setGenresFunction() {
        return book -> {
            List<String> genreIds = book.getGenres().stream()
                    .map(Genre::getId)
                    .collect(Collectors.toList());
            return genreRepository.findByIdIn(genreIds).collectList()
                    .map(genres -> {
                        book.setGenres(genres);
                        return book;
                    });
        };
    }
}
