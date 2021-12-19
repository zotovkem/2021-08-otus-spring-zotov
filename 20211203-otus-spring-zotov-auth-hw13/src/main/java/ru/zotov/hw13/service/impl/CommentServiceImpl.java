package ru.zotov.hw13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.GrantedAuthoritySid;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.acls.model.ObjectIdentity;
import org.springframework.security.acls.model.Sid;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw13.dao.CommentRepository;
import ru.zotov.hw13.domain.Book;
import ru.zotov.hw13.domain.Comment;
import ru.zotov.hw13.service.CommentService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 17.12.2021
 * Реализация сервиса коментариев
 */
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final MutableAclService mutableAclService;

    /**
     * Сохранить список комментариев
     *
     * @param incomingBookComments список комментариев
     * @param bookId               ид книги
     * @return список сохраненных комментариев
     */
    @Override
    @Transactional
    public List<Comment> saveListComments(@NonNull List<Comment> incomingBookComments, @NonNull Long bookId) {
        incomingBookComments.forEach(comment -> {
            Book book = Book.builder().id(bookId).build();
            comment.setBook(book);
            if (comment.getCreateDate() == null) {
                comment.setCreateDate(ZonedDateTime.now());
            }
        });

        List<Long> commentsIds = incomingBookComments.stream().map(Comment::getId).collect(Collectors.toList());
        List<Long> deletedCommentIds = incomingBookComments.stream()
                .map(Comment::getId)
                .filter(id -> !commentsIds.contains(id))
                .collect(Collectors.toList());
        if (!deletedCommentIds.isEmpty()) {
            commentRepository.deleteAllByIdInBatch(deletedCommentIds);
        }

        return commentRepository.saveAll(incomingBookComments);
    }

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
        Comment savedComment = commentRepository.save(comment);
        setAdminAcl(savedComment);
        return savedComment;
    }

    /**
     * Редактировать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    @PreAuthorize("hasPermission(#comment,'WRITE')")
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
    public Optional<Comment> findById(Long commentId) {
        return commentRepository.findById(commentId);
    }

    /**
     * Удалить комментарии по списку ид
     *
     * @param ids список ид комментариев
     */
    @SuppressWarnings("UseBulkOperation")
    @Override
    @Transactional
    public void deleteByIds(List<Long> ids) {
        List<Comment> commentList = commentRepository.findAllById(ids);
        commentList.forEach(commentRepository::delete);

        commentList.forEach(comment -> {
            ObjectIdentity oid = new ObjectIdentityImpl(comment.getClass(), comment.getId());
            mutableAclService.deleteAcl(oid, true);
        });
    }

    private void setAdminAcl(Comment comment) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(comment.getClass(), comment.getId());

        authentication.getAuthorities().stream()
                .map(GrantedAuthoritySid::new)
                .forEach(grantedAuthority -> {
                    MutableAcl acl = mutableAclService.createAcl(oid);
                    acl.setOwner(owner);
                    acl.insertAce(acl.getEntries().size(), BasePermission.DELETE, grantedAuthority, true);
                    acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, grantedAuthority, true);

                    mutableAclService.updateAcl(acl);
                });
    }
}

