package ru.zotov.hw10.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.service.BookService;

import java.util.List;

/**
 * @author Created by ZotovES on 29.09.2021
 * Контроллер книг
 */
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;

    /**
     * Создание книги
     *
     * @param book книга
     */
    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    /**
     * Получить все книги
     *
     * @return список книг
     */
    @GetMapping
    public List<Book> bookGetAll() {
        return bookService.findByAll();
    }

    /**
     * Найти книги по наименованию
     *
     * @param name наименование
     * @return список книг
     */
    @GetMapping("/find-by-name")
    public List<Book> findByName(@RequestParam("name") String name) {
        return bookService.findByName(name);
    }

    /**
     * Найти книги по жанру
     *
     * @param name наименование жанра
     * @return список книг
     */
    @GetMapping("/find-by-genre-name")
    public List<Book> findByGenreName(@RequestParam("genre-name") String name) {
        return bookService.findByGenreName(name);
    }

    /**
     * Поиск книг по фио автора
     *
     * @param fio фио автора
     * @return список книг
     */
    @GetMapping("/find-by-author-fio")
    public List<Book> findByAuthorFio(@RequestParam("author-fio") String fio) {
        return bookService.findByAuthorFio(fio);
    }

    /**
     * Получить книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @GetMapping("/{id}")
    public Book getById(@PathVariable("id") String id) {
        return bookService.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found book by id = " + id));
    }

    /**
     * Редактирование книги
     *
     * @param book книга
     */
    @PutMapping
    public Book updateBook(@RequestBody Book book) {
        return bookService.save(book);
    }

    /**
     * Удалить книгу по ид
     *
     * @param ids список ид
     */
    @DeleteMapping
    public void deleteById(@RequestBody List<String> ids) {
        bookService.deleteByIds(ids);
    }
}
