package ru.zotov.hw8.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.zotov.hw8.dao.AuthorRepository;
import ru.zotov.hw8.dao.BookRepository;
import ru.zotov.hw8.dao.CommentRepository;
import ru.zotov.hw8.dao.GenreRepository;
import ru.zotov.hw8.domain.Author;
import ru.zotov.hw8.domain.Book;
import ru.zotov.hw8.domain.Comment;
import ru.zotov.hw8.domain.Genre;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {
    private List<Genre> genreList;
    private List<Author> authorList;
    private List<Book> bookList;

//    @ChangeSet(order = "001", id = "dropDb", author = "stvort", runAlways = true)
//    public void dropDb(MongoDatabase db) {
//        db.drop();
//}

    @ChangeSet(order = "000", id = "initCollection", author = "ezotov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

//    @ChangeSet(order = "000", id = "addBook", author = "ezotov")
//    public void insertLermontov(MongoDatabase db) {
//        MongoCollection<Document> myCollection = db.getCollection("persons");
//        var doc = new Document().append("name", "Lermontov");
//        myCollection.insertOne(doc);
//    }

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
                        8, "Александра Маринина").entrySet().stream()
                .map(e -> new Author(e.getKey().toString(), e.getValue()))
                .collect(Collectors.toList());

        authorList = authorRepository.saveAll(authors);
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
                        8, "Аудио книги").entrySet().stream()
                .map(e -> new Genre(e.getKey().toString(), e.getValue()))
                .collect(Collectors.toList());

        genreList = genreRepository.saveAll(genres);
    }

    /**
     * Заполнение коллекции книг
     */
    @ChangeSet(order = "003", id = "fillBooks", author = "ezotov")
    public void addBooks(BookRepository bookRepository) {
        List<Book> books = List.of(
                new Book("1", "Высоконагруженные приложения", 2017, getAuthorSet(Set.of("2", "6")),
                        getGenreSet(Set.of("2", "5"))),
                new Book("2", "Чистая архитектура", 2018, getAuthorSet(Set.of("1", "7")), getGenreSet(Set.of("2", "7"))),
                new Book("3", "Правильное питание", 2021, getAuthorSet("3"), getGenreSet("4")),
                new Book("4", "Экстремальная археология", 2021, getAuthorSet("5"), getGenreSet("4")),
                new Book("5", "Сказки пушкина", 2008, getAuthorSet("4"), getGenreSet(Set.of("3", "8"))),
                new Book("6", "Отдаленные последствия", 2021, getAuthorSet("8"), getGenreSet("1")));

        bookList = bookRepository.saveAll(books);
    }

    /**
     * Заполнение коллекции комментариев к книгам
     */
    @ChangeSet(order = "004", id = "fillComments", author = "ezotov")
    public void addBookComments(CommentRepository commentsRepository) {
        List<Comment> comments = List.of(
                new Comment("1", getBookById("1"), "Вроде не чего, еще не дочитал", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("2", getBookById("2"), "Хорошая книга", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("3", getBookById("3"), "Не про гречку", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("4", getBookById("4"), "Странное название", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("6", getBookById("6"), "Детектива, детектива", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("7", getBookById("1"), "Комментарий ", "Иванов",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("8", getBookById("2"), "Тестовый комментарий", "Петров",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("9", getBookById("3"), "Еще один комментарий", "Сидоров",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("10", getBookById("4"), "Как много комментарием нужно написать", "Тестов",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new Comment("12", getBookById("6"), "Последний комментарий", "Лютый критик",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")));

        commentsRepository.saveAll(comments);
    }

    private Book getBookById(String id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findAny().orElse(null);
    }

    private Set<Author> getAuthorSet(String id) {
        return getAuthorSet(Set.of(id));
    }

    private Set<Author> getAuthorSet(Set<String> ids) {
        return authorList.stream().filter(author -> ids.contains(author.getId())).collect(Collectors.toSet());
    }

    private Set<Genre> getGenreSet(Set<String> ids) {
        return genreList.stream().filter(genre -> ids.contains(genre.getId())).collect(Collectors.toSet());
    }

    private Set<Genre> getGenreSet(String s) {
        return getGenreSet(Set.of(s));
    }
}
