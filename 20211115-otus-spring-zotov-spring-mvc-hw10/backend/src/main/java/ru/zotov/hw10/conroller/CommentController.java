package ru.zotov.hw10.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zotov.hw10.domain.Comment;
import ru.zotov.hw10.service.CommentService;

/**
 * @author Created by ZotovES on 22.10.2021
 * Контроллер управления комментариями
 */
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    /**
     * Добавить комментарий к книге
     *
     * @param bookId  ид книги
     * @param comment комментарий
     */
    @PostMapping
    public void addCommentBook(@RequestBody Comment comment) {
//        commentService.create( comment,bookId);
    }
//
//    /**
//     * Редактировать комментарий к книге
//     * Пример comment-update -id 1 -text "Текст комментария измененный" -author Зотов
//     *
//     * @param id             ид комментария
//     * @param commentContent текст комментария
//     * @param author         автор комментария
//     */
//    @ShellMethod(value = "Update comment for book", key = {"comment-update"})
//    public void updateCommentBook(@ShellOption("-id") String id, @ShellOption("-text") String commentContent,
//            @ShellOption("-author") String author) {
//        commentService.update(new Comment(id, commentContent, author,
//                ZonedDateTime.now()));
//    }
//
//    /**
//     * Удалить комментарий по ид
//     * Пример comment-delete-by-id 1
//     */
//    @ShellMethod(value = "Delete comment by id", key = {"comment-delete-by-id"})
//    public void deleteCommentBook(String id) {
//        commentService.deleteById(id);
//    }
//
//    /**
//     * Получить все комментарии
//     *
//     * @return список комментариев
//     */
//    @ShellMethod(value = "Get all comments", key = {"comment-get-all"})
//    public List<Comment> getAll() {
//        return commentService.findAll();
//    }
//
//    /**
//     * Получить комментарий по ид
//     *
//     * @param id ид комментария
//     * @return комментарий
//     */
//    @ShellMethod(value = "Get Comment by id", key = {"comment-get-by-id"})
//    public Comment getById(String id) {
//        return commentService.findById(id);
//    }
//
//    /**
//     * Поиск список комментариев по ид книги
//     *
//     * @param bookId ид книги
//     * @return список комментариев
//     */
//    @ShellMethod(value = "Find comment by bookId", key = {"comment-find-by-book-id"})
//    public List<Comment> findByBookId(String bookId) {
//        return commentService.findByBookId(bookId);
//    }
}
