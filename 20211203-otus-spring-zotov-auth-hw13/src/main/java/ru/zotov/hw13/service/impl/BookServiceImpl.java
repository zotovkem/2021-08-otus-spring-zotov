package ru.zotov.hw13.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PostAuthorize;
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
import ru.zotov.hw13.dao.BookRepository;
import ru.zotov.hw13.domain.Book;
import ru.zotov.hw13.service.BookService;

import java.util.List;

/**
 * @author Created by ZotovES on 07.10.2021
 * Реализация сервиса управления книгами
 */
@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {
    private final BookRepository bookDao;
    private final MutableAclService aclService;

    /**
     * Редактировать книгу
     *
     * @param book Книга
     * @return книга
     */
    @Override
    public Book update(Book book) {
        return bookDao.save(book);
    }

    /**
     * Создать книгу
     *
     * @param book Книга
     * @return книга
     */
    @Override
    @Transactional
    public Book create(Book book) {
        book.setId(null);
        Book savedBook = bookDao.save(book);
        grantedPermissionForAdult(savedBook);

        return savedBook;
    }

    /**
     * Удалить книгу по ид
     *
     * @param ids список ид
     */
    @Override
    public void deleteByIds(List<Long> ids) {
        bookDao.deleteAllByIdInBatch(ids);

        ids.forEach(id -> {
            ObjectIdentity oid = new ObjectIdentityImpl(Book.class, id);
            aclService.deleteAcl(oid, true);
        });
    }

    /**
     * Получить список всех книг
     *
     * @return список книг
     */
    @Override
    public List<Book> findByAll() {
        return bookDao.findAll();
    }

    /**
     * Найти книгу по ид
     *
     * @param id ид
     * @return книга
     */
    @Override
    @Nullable
    @PostAuthorize("hasPermission(returnObject, 'READ') or hasAnyRole('ADMIN')")
    public Book findById(Long id) {
        return bookDao.findById(id).orElse(null);
    }

    /**
     * Выдаем права на чтение для группы взрослых
     *
     * @param book книга
     */
    private void grantedPermissionForAdult(Book book) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        final Sid owner = new PrincipalSid(authentication);
        ObjectIdentity oid = new ObjectIdentityImpl(book.getClass(), book.getId());

        final Sid grantedAuthority = new GrantedAuthoritySid("ROLE_ADULT");
        MutableAcl acl = aclService.createAcl(oid);
        acl.setOwner(owner);
        acl.insertAce(acl.getEntries().size(), BasePermission.READ, grantedAuthority, true);

        aclService.updateAcl(acl);
    }
}
