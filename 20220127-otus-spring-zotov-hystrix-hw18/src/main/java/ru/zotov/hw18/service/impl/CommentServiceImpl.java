package ru.zotov.hw18.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw18.dao.CommentRepository;
import ru.zotov.hw18.domain.Comment;
import ru.zotov.hw18.service.CommentService;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * @author Created by ZotovES on 17.12.2021
 * Реализация сервиса коментариев
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    /**
     * Создать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    @Transactional
    public Comment create(Comment comment) {
        comment.setCreateDate(ZonedDateTime.now());
        comment.setId(null);
        Comment savedComment = commentRepository.save(comment);
        return savedComment;
    }

    /**
     * Редактировать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    public Comment update(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * Получить список всех комментариев
     *
     * @return список комментариев
     */
    @Override
    public List<Comment> findByAll() {
        return commentRepository.findAll();
    }

    /**
     * Найти комментарий по ид
     *
     * @param commentId ид комментария
     * @return комментарий
     */
    @Override
    @Nullable
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    /**
     * Удалить комментарии по списку ид
     *
     * @param ids список ид комментариев
     */
    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        commentRepository.deleteAllById(ids);
    }

}

