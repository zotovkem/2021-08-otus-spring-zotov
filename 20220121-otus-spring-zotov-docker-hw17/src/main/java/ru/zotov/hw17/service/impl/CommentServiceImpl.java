package ru.zotov.hw17.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PostAuthorize;
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
import ru.zotov.hw17.dao.CommentRepository;
import ru.zotov.hw17.domain.Comment;
import ru.zotov.hw17.service.CommentService;

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
    private final MutableAclService mutableAclService;

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
        setPermissionOwnerAcl(savedComment);
        return savedComment;
    }

    /**
     * Редактировать комментарий
     *
     * @param comment комментарий
     * @return комментарий
     */
    @Override
    @PreAuthorize("hasPermission(#comment,'WRITE') or hasRole('ADMIN')")
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
    @PostAuthorize("hasPermission(returnObject, 'READ') or !hasAnyRole('CHILD')")
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
        List<Comment> commentList = commentRepository.findAllById(ids);

        commentList.forEach(comment -> {
            commentRepository.delete(comment);
            ObjectIdentity oid = new ObjectIdentityImpl(comment.getClass(), comment.getId());
            mutableAclService.deleteAcl(oid, true);
        });
    }

    /**
     * Устанавливает права на редактирование и удаление на комментарий
     *
     * @param comment комментарий
     */
    private void setPermissionOwnerAcl(Comment comment) {
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

