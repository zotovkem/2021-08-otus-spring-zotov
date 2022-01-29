package ru.zotov.hw17.conroller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw17.domain.Book;
import ru.zotov.hw17.dto.BookDto;
import ru.zotov.hw17.service.BookService;

import java.util.List;
import java.util.Optional;
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
        return mapper.map(bookService.create(book), BookDto.class);
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
     * Получить книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @GetMapping("/{id}")
    public ResponseEntity<BookDto> getById(@PathVariable("id") Long id) {
        return Optional.ofNullable(bookService.findById(id))
                .map(book -> mapper.map(book, BookDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Редактирование книги
     *
     * @param bookDto dto книги
     */
    @PutMapping
    public BookDto updateBook(@RequestBody BookDto bookDto) {
        Book book = mapper.map(bookDto, Book.class);
        return mapper.map(bookService.update(book), BookDto.class);
    }

    /**
     * Удалить книгу по ид
     *
     * @param ids список ид
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@RequestBody List<Long> ids) {
        bookService.deleteByIds(ids);
    }
}
