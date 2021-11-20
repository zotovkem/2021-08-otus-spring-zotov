package ru.zotov.hw10.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw10.domain.Book;
import ru.zotov.hw10.domain.Comment;
import ru.zotov.hw10.service.BookService;
import ru.zotov.hw10.service.CommentService;

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
    private final CommentService commentService;

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

    /**
     * Добавить комментарий к книге
     *
     * @param bookId  ид книги
     * @param comment комментарий
     */
    @PostMapping("/{bookId}/comments")
    public Comment addCommentBook(@RequestBody Comment comment, @PathVariable("bookId") String bookId) {
        return commentService.create(comment, bookId);
    }

    /**
     * Редактировать комментарий к книге
     *
     * @param comment комментарий
     */
    @PutMapping("/{bookId}/comments")
    public Comment updateCommentBook(@RequestBody Comment comment, @PathVariable("bookId") String bookId) {
        return commentService.update(comment, bookId);
    }

    /**
     * Удалить комментарий по списку ид
     */
    @DeleteMapping("/{bookId}/comments")
    public void deleteCommentBook(@RequestBody List<String> ids, @PathVariable("bookId") String bookId) {
        commentService.deleteByIds(ids, bookId);
    }

    /**
     * Получить все комментарии
     *
     * @return список комментариев
     */
    @GetMapping("/{bookId}/comments")
    public List<Comment> getAll(@PathVariable("bookId") String bookId) {
        return commentService.findByBookId(bookId);
    }

    /**
     * Получить комментарий по ид
     *
     * @param id ид комментария
     * @return комментарий
     */
    @GetMapping("/{bookId}/comments/{id}")
    public Comment findCommentById(@PathVariable("id") String id, @PathVariable("bookId") String bookid) {
        return commentService.findById(id, bookid);
    }
}
