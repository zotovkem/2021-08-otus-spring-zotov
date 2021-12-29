package ru.zotov.hw14.changelogs;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.mongodb.client.MongoDatabase;
import ru.zotov.hw14.dao.AuthorRepositoryMongo;
import ru.zotov.hw14.dao.BookRepositoryMongo;
import ru.zotov.hw14.dao.GenreRepositoryMongo;
import ru.zotov.hw14.domain.AuthorMongo;
import ru.zotov.hw14.domain.BookMongo;
import ru.zotov.hw14.domain.CommentMongo;
import ru.zotov.hw14.domain.GenreMongo;

import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("unused")
@ChangeLog
public class DatabaseChangelog {
    private List<GenreMongo> genreList;
    private List<AuthorMongo> authorList;
    private List<BookMongo> bookList;
    private List<CommentMongo> commentList;

    @ChangeSet(order = "000", id = "initCollection", author = "ezotov", runAlways = true)
    public void dropDb(MongoDatabase db) {
        db.drop();
    }

    /**
     * Заполнение коллекции авторов
     */
    @ChangeSet(order = "001", id = "fillAuthor", author = "ezotov")
    public void fillAuthor(AuthorRepositoryMongo authorRepository) {
        List<AuthorMongo> authors = Map.of(1, "Роберт Мартин",
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
                .map(e -> new AuthorMongo(e.getKey().toString(), e.getValue()))
                .collect(Collectors.toList());

        authorList = authorRepository.saveAll(authors);
    }

    /**
     * Заполнение коллекции жанров
     */
    @ChangeSet(order = "002", id = "fillGenre", author = "ezotov")
    public void fillGenre(GenreRepositoryMongo genreRepository) {
        List<GenreMongo> genres = Map.of(1, "Детектив",
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
                .map(e -> new GenreMongo(e.getKey().toString(), e.getValue()))
                .collect(Collectors.toList());

        genreList = genreRepository.saveAll(genres);
    }

    /**
     * Заполнение коллекции комментариев к книгам
     */
    @ChangeSet(order = "003", id = "fillComments", author = "ezotov")
    public void addBookComments() {
        commentList = List.of(
                new CommentMongo("1", "Вроде не чего, еще не дочитал", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("2", "Хорошая книга", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("3", "Не про гречку", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("4", "Странное название", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("6", "Детектива, детектива", "ЗотовЕС",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("7", "Комментарий ", "Иванов",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("8", "Тестовый комментарий", "Петров",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("9", "Еще один комментарий", "Сидоров",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("10", "Как много комментарием нужно написать", "Тестов",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")),
                new CommentMongo("12", "Последний комментарий", "Лютый критик",
                        ZonedDateTime.parse("2020-02-01T19:10:25+07:00")));
    }

    /**
     * Заполнение коллекции книг
     */
    @ChangeSet(order = "004", id = "fillBooks", author = "ezotov")
    public void addBooks(BookRepositoryMongo bookRepository) {
        List<BookMongo> books = List.of(
                new BookMongo("1", "Высоконагруженные приложения", 2017, getAuthorSet(Set.of("2", "6")),
                        getGenreSet(Set.of("2", "5")), getCommentList(Set.of("1", "7"))),
                new BookMongo("2", "Чистая архитектура", 2018, getAuthorSet(Set.of("1", "7")),
                        getGenreSet(Set.of("2", "7")), getCommentList(Set.of("2", "8"))),
                new BookMongo("3", "Правильное питание", 2021, getAuthorSet("3"),
                        getGenreSet("4"), getCommentList(Set.of("3", "9"))),
                new BookMongo("4", "Экстремальная археология", 2021, getAuthorSet("5"),
                        getGenreSet("4"), getCommentList(Set.of("4", "10"))),
                new BookMongo("5", "Сказки пушкина", 2008, getAuthorSet("4"), getGenreSet(Set.of("3", "8")), Collections.emptyList()),
                new BookMongo("6", "Отдаленные последствия", 2021, getAuthorSet("8"),
                        getGenreSet("1"), getCommentList(Set.of("6", "12"))));

        bookList = bookRepository.saveAll(books);
    }

    private BookMongo getBookById(String id) {
        return bookList.stream().filter(book -> book.getId().equals(id)).findAny().orElse(null);
    }

    private Set<AuthorMongo> getAuthorSet(String id) {
        return getAuthorSet(Set.of(id));
    }

    private Set<AuthorMongo> getAuthorSet(Set<String> ids) {
        return authorList.stream().filter(author -> ids.contains(author.getId())).collect(Collectors.toSet());
    }

    private Set<GenreMongo> getGenreSet(Set<String> ids) {
        return genreList.stream().filter(genre -> ids.contains(genre.getId())).collect(Collectors.toSet());
    }

    private Set<GenreMongo> getGenreSet(String s) {
        return getGenreSet(Set.of(s));
    }

    private List<CommentMongo> getCommentList(Set<String> ids) {
        return commentList.stream().filter(comment -> ids.contains(comment.getId())).collect(Collectors.toList());
    }

    private List<CommentMongo> getCommentList(String s) {
        return getCommentList(Set.of(s));
    }
}
