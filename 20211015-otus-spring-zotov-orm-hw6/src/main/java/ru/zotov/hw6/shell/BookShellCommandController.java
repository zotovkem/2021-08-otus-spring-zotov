package ru.zotov.hw6.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.zotov.hw6.domain.Author;
import ru.zotov.hw6.domain.Book;
import ru.zotov.hw6.domain.Genre;
import ru.zotov.hw6.service.BookService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 29.09.2021
 * Контроллер shell команд книг
 */
@SuppressWarnings("unused")
@ShellComponent
@RequiredArgsConstructor
public class BookShellCommandController {
    private final BookService bookService;

    /**
     * Создание книги
     * Пример команды book-create -name Мемуары -release-year 2021 -author-ids 1 5 0 0 0 -genre-ids 8 5 0 0 0
     *
     * @param name        наименование
     * @param releaseYear годы издания
     * @param authorIds   список ид авторов
     * @param genreIds    список ид жанров
     */
    @ShellMethod(value = "Create new book", key = {"book-create"})
    public Book createBook(@ShellOption("-name") String name, @ShellOption("-release-year") Integer releaseYear,
            @ShellOption(arity = 5, value = "-author-ids") long[] authorIds,
            @ShellOption(arity = 5, value = "-genre-ids") long[] genreIds) {
        Book book = Book.builder()
                .name(name)
                .releaseYear(releaseYear)
                .authors(Arrays.stream(authorIds).filter(id -> id > 0).mapToObj(id -> new Author(id, null))
                        .collect(Collectors.toList()))
                .genres(Arrays.stream(genreIds).filter(id -> id > 0).mapToObj(id -> new Genre(id, null))
                        .collect(Collectors.toList()))
                .build();
        return bookService.create(book);
    }

    /**
     * Получить все книги
     *
     * @return список книг
     */
    @ShellMethod(value = "Get all books", key = {"book-get-all"})
    public List<Book> bookGetAll() {
        return bookService.findByAll();
    }

    /**
     * Найти книги по наименованию
     *
     * @param name наименование
     * @return список книг
     */
    @ShellMethod(value = "Find books by name", key = {"book-find-by-name"})
    public List<Book> findByName(String name) {
        return bookService.findByName(name);
    }

    /**
     * Найти книги по жанру
     *
     * @param name наименование жанра
     * @return список книг
     */
    @ShellMethod(value = "Find books by genre name", key = {"book-find-by-genre-name"})
    public List<Book> findByGenreName(String name) {
        return bookService.findByGenreName(name);
    }

    /**
     * Поиск книг по фио автора
     *
     * @param fio фио автора
     * @return список книг
     */
    @ShellMethod(value = "Find books by fio author", key = {"book-find-by-fio-author"})
    public List<Book> findByAuthorFio(String fio) {
        return bookService.findByAuthorFio(fio);
    }

    /**
     * Получить книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @ShellMethod(value = "Get book by id", key = "book-get-id")
    public Book getById(Long id) {
        return bookService.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found book by id = " + id));
    }

    /**
     * Создание книги
     * Пример команды book-update -id 1 -name Мемуары -release-year 2021 -author-ids 1 5 0 0 0 -genre-ids 8 5 0 0 0
     *
     * @param name        наименование
     * @param releaseYear годы издания
     * @param authorIds   список ид авторов
     * @param genreIds    список ид жанров
     */
    @ShellMethod(value = "Update book info", key = {"book-update"})
    public Book updateBook(@ShellOption("-id") Long bookId, @ShellOption("-name") String name,
            @ShellOption("-release-year") Integer releaseYear,
            @ShellOption(arity = 5, value = "-author-ids") long[] authorIds,
            @ShellOption(arity = 5, value = "-genre-ids") long[] genreIds) {
        Book book = Book.builder()
                .id(bookId)
                .name(name)
                .releaseYear(releaseYear)
                .authors(Arrays.stream(authorIds).filter(id -> id > 0).mapToObj(id -> new Author(id, null))
                        .collect(Collectors.toList()))
                .genres(Arrays.stream(genreIds).filter(id -> id > 0).mapToObj(id -> new Genre(id, null))
                        .collect(Collectors.toList()))
                .build();
        return bookService.update(book);
    }

    /**
     * Удалить книгу по ид
     *
     * @param id ид
     */
    @ShellMethod(value = "Delete book by id", key = "book-delete-by-id")
    public void deleteById(Long id) {
        bookService.deleteById(id);
    }
}
