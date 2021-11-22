package ru.zotov.hw10.conroller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.dto.BookDto;
import ru.zotov.hw10.service.BookService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 29.09.2021
 * Контроллер книг
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final ModelMapper mapper;
    private final BookService bookService;

    /**
     * Создание книги
     *
     * @param bookDto dto книги
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BookDto createBook(@RequestBody BookDto bookDto) {
        Book book = mapper.map(bookDto, Book.class);
        return mapper.map(bookService.save(book), BookDto.class);
    }

    /**
     * Получить все книги
     *
     * @return список книг
     */
    @GetMapping
    public List<BookDto> bookGetAll() {
        return bookService.findByAll().stream()
                .map(book -> mapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Найти книги по наименованию
     *
     * @param name наименование
     * @return список книг
     */
    @GetMapping("/find-by-name")
    public List<BookDto> findByName(@RequestParam("name") String name) {
        return bookService.findByName(name).stream()
                .map(book -> mapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Найти книги по жанру
     *
     * @param name наименование жанра
     * @return список книг
     */
    @GetMapping("/find-by-genre-name")
    public List<BookDto> findByGenreName(@RequestParam("genre-name") String name) {
        return bookService.findByGenreName(name).stream()
                .map(book -> mapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Поиск книг по фио автора
     *
     * @param fio фио автора
     * @return список книг
     */
    @GetMapping("/find-by-author-fio")
    public List<BookDto> findByAuthorFio(@RequestParam("author-fio") String fio) {
        return bookService.findByAuthorFio(fio).stream()
                .map(book -> mapper.map(book, BookDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @GetMapping("/{id}")
    public BookDto getById(@PathVariable("id") String id) {
        return bookService.findById(id)
                .map(book -> mapper.map(book, BookDto.class))
                .orElseThrow(() -> new IllegalArgumentException("Not found book by id = " + id));
    }

    /**
     * Редактирование книги
     *
     * @param bookDto dto книги
     */
    @PutMapping
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        Book book = mapper.map(bookDto, Book.class);
        return mapper.map(bookService.save(book), BookDto.class);
    }

    /**
     * Удалить книгу по ид
     *
     * @param ids список ид
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestBody List<String> ids) {
        bookService.deleteByIds(ids);
    }
}
