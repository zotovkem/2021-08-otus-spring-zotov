package ru.zotov.hw8.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.zotov.hw8.domain.Comment;
import ru.zotov.hw8.service.CommentService;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Created by ZotovES on 22.10.2021
 * Контроллер команд управления комментариями
 */
@SuppressWarnings("unused")
@ShellComponent
@RequiredArgsConstructor
public class CommentShellCommandController {
    private final CommentService commentService;

    /**
     * Добавить комментарий к книге
     * Пример comment-add -book-id 1 -text "Текст комментария" -author Зотов
     *
     * @param bookId         ид книги
     * @param commentContent текст комментария
     * @param author         автор комментария
     */
    @ShellMethod(value = "Add comment for book", key = {"comment-add"})
    public void addCommentBook(@ShellOption("-book-id") String bookId, @ShellOption("-text") String commentContent,
            @ShellOption("-author") String author) {
        commentService.create(new Comment(null, commentContent, author,
                ZonedDateTime.now()), bookId);
    }

    /**
     * Редактировать комментарий к книге
     * Пример comment-update -id 1 -text "Текст комментария измененный" -author Зотов
     *
     * @param id             ид комментария
     * @param commentContent текст комментария
     * @param author         автор комментария
     */
    @ShellMethod(value = "Update comment for book", key = {"comment-update"})
    public void updateCommentBook(@ShellOption("-id") String id, @ShellOption("-text") String commentContent,
            @ShellOption("-author") String author) {
        commentService.update(new Comment(id, commentContent, author,
                ZonedDateTime.now()));
    }

    /**
     * Удалить комментарий по ид
     * Пример comment-delete-by-id 1
     */
    @ShellMethod(value = "Delete comment by id", key = {"comment-delete-by-id"})
    public void deleteCommentBook(String id) {
        commentService.deleteById(id);
    }

    /**
     * Получить все комментарии
     *
     * @return список комментариев
     */
    @ShellMethod(value = "Get all comments", key = {"comment-get-all"})
    public List<Comment> getAll() {
        return commentService.findAll();
    }

    /**
     * Получить комментарий по ид
     *
     * @param id ид комментария
     * @return комментарий
     */
    @ShellMethod(value = "Get Comment by id", key = {"comment-get-by-id"})
    public Comment getById(String id) {
        return commentService.findById(id);
    }

    /**
     * Поиск список комментариев по ид книги
     *
     * @param bookId ид книги
     * @return список комментариев
     */
    @ShellMethod(value = "Find comment by bookId", key = {"comment-find-by-book-id"})
    public List<Comment> findByBookId(String bookId) {
        return commentService.findByBookId(bookId);
    }
}
