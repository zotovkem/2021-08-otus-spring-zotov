package ru.zotov.hw13.conroller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw13.domain.Book;
import ru.zotov.hw13.domain.Comment;
import ru.zotov.hw13.dto.CommentDto;
import ru.zotov.hw13.service.CommentService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 18.12.2021
 * Контроллер комментариев
 */
@RestController
@RequestMapping(value = "/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final ModelMapper mapper;
    private final CommentService commentService;

    /**
     * Создать комментарий
     *
     * @param commentDto дто комментария
     * @return дто комментария
     */
    @PostMapping
    @PreAuthorize("!hasAnyRole('CHILD')")
    @ResponseStatus(HttpStatus.CREATED)
    public CommentDto createComment(@RequestBody CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setBook(Book.builder().id(commentDto.getBookId()).build());

        return mapper.map(commentService.create(comment), CommentDto.class);
    }

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    @GetMapping
    public List<CommentDto> commentsGetAll() {
        return commentService.findByAll().stream()
                .map(comment -> mapper.map(comment, CommentDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Получить комментарий по ид
     */
    @GetMapping("/{id}")
    public ResponseEntity<CommentDto> getCommentById(@PathVariable("id") Long commentId) {
        return commentService.findById(commentId)
                .map(comment -> mapper.map(comment, CommentDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Редактировать комментарий
     *
     * @return комментарий
     */
    @PutMapping
    @PreAuthorize("!hasRole('CHILD')")
    public CommentDto updateComment(@RequestBody CommentDto commentDto) {
        Comment comment = mapper.map(commentDto, Comment.class);
        comment.setBook(Book.builder().id(commentDto.getId()).build());

        return mapper.map(commentService.update(comment), CommentDto.class);
    }

    /**
     * Удалить комментарии по списку ид
     */
    @DeleteMapping
    @PreAuthorize("!hasRole('CHILD')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteByIds(@RequestBody List<Long> ids) {
        commentService.deleteByIds(ids);
    }
}