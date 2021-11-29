package ru.zotov.hw11.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.zotov.hw11.dao.AuthorRepository;
import ru.zotov.hw11.dao.BookRepository;
import ru.zotov.hw11.dao.GenreRepository;
import ru.zotov.hw11.domain.Author;
import ru.zotov.hw11.domain.Book;
import ru.zotov.hw11.domain.Comment;
import ru.zotov.hw11.domain.Genre;

import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {
    private List<Genre> genreList;
    private List<Author> authorList;
    private List<Book> bookList;
    private List<Comment> commentList;

    @ChangeSet(order = "000", id = "initCollection", author = "ezotov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    /**
     * Заполнение коллекции авторов
     */
    @ChangeSet(order = "001", id = "fillAuthor", author = "ezotov")
    public void fillAuthor(AuthorRepository authorRepository) {
        List<Author> authors = Map.of(1, "Роберт Мартин",
                        2, "Мартин Клеппман",
                        3, "Билл Любанович",
                        4, "Александр Сергеевич Пушкин",
                        5, "Артем Каменистый",
                        6, "А. Тумаркин",
                        7, "Александр Киселев",
                        8, "Александра Маринина",
                        9, "Иванов",
                        10, "Петров")
                .entrySet().stream()
                .map(e -> new Author(e.getKey().toString(), e.getValue()))
                .collect(Collectors.toList());

        authorList = authorRepository.saveAll(authors).collectList().block();
    }

    /**
     * Заполнение коллекции жанров
     */
    @ChangeSet(order = "002", id = "fillGenre", author = "ezotov")
    public void fillGenre(GenreRepository genreRepository) {
        List<Genre> genres = Map.of(1, "Детектив",
                        2, "Компьютерная литература",
                        3, "Сказки",
                        4, "Фантастика",
                        5, "Программирование",
                        6, "Базы данных",
                        7, "Архитектура ИС",
                        8, "Аудио книги",
                        9, "Стихи",
                        10, "Басни")
                .entrySet().stream()
                .map(e -> new Genre(e.getKey().toString(), e.getValue()))
                .collect(Collectors.toList());

        genreList = genreRepository.saveAll(genres).collectList().block();
    }

    /**
     * Заполнение коллекции комментариев к книгам
     */
    @ChangeSet(order = "003", id = "fillComments", author = "ezotov")
    public void addBookComments() throws ParseException {
        commentList = List.of(
                new Comment("1", "Вроде не чего, еще не дочитал", "ЗотовЕС",
                        new Date()),
                new Comment("2", "Хорошая книга", "ЗотовЕС",
                        new Date()),
                new Comment("3", "Не про гречку", "ЗотовЕС",
                        new Date()),
                new Comment("4", "Странное название", "ЗотовЕС",
                        new Date()),
                new Comment("6", "Детектива, детектива", "ЗотовЕС",
                        new Date()),
                new Comment("7", "Комментарий ", "Иванов",
                        new Date()),
                new Comment("8", "Тестовый комментарий", "Петров",
                        new Date()),
                new Comment("9", "Еще один комментарий", "Сидоров",
                        new Date()),
                new Comment("10", "Как много комментарием нужно написать", "Тестов",
                        new Date()),
                new Comment("12", "Последний комментарий", "Лютый критик",
                        new Date()));
    }

    /**
     * Заполнение коллекции книг
     */
    @ChangeSet(order = "004", id = "fillBooks", author = "ezotov")
    public void addBooks(BookRepository bookRepository) {
        List<Book> books = List.of(
                new Book("1", "Высоконагруженные приложения", 2017, getAuthorSet(Set.of("2", "6")),
                        getGenreSet(Set.of("2", "5")), getCommentList(Set.of("1", "7"))),
                new Book("2", "Чистая архитектура", 2018, getAuthorSet(Set.of("1", "7")),
                        getGenreSet(Set.of("2", "7")), getCommentList(Set.of("2", "8"))),
                new Book("3", "Правильное питание", 2021, getAuthorSet("3"),
                        getGenreSet("4"), getCommentList(Set.of("3", "9"))),
                new Book("4", "Экстремальная археология", 2021, getAuthorSet("5"),
                        getGenreSet("4"), getCommentList(Set.of("4", "10"))),
                new Book("5", "Сказки пушкина", 2008, getAuthorSet("4"), getGenreSet(Set.of("3", "8")), Collections.emptyList()),
                new Book("6", "Отдаленные последствия", 2021, getAuthorSet("8"),
                        getGenreSet("1"), getCommentList(Set.of("6", "12"))));

        bookList = bookRepository.saveAll(books).collectList().block();
    }

    private Book getBookById(String id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findAny().orElse(null);
    }

    private List<Author> getAuthorSet(String id) {
        return getAuthorSet(Set.of(id));
    }

    private List<Author> getAuthorSet(Set<String> ids) {
        return authorList.stream().filter(author -> ids.contains(author.getId())).collect(Collectors.toList());
    }

    private List<Genre> getGenreSet(Set<String> ids) {
        return genreList.stream().filter(genre -> ids.contains(genre.getId())).collect(Collectors.toList());
    }

    private List<Genre> getGenreSet(String s) {
        return getGenreSet(Set.of(s));
    }

    private List<Comment> getCommentList(Set<String> ids) {
        return commentList.stream().filter(comment -> ids.contains(comment.getId())).collect(Collectors.toList());
    }

    private List<Comment> getCommentList(String s) {
        return getCommentList(Set.of(s));
    }
}
