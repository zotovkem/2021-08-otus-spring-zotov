package ru.zotov.hw6.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw6.dao.AuthorRepository;
import ru.zotov.hw6.domain.Author;
import ru.zotov.hw6.service.AuthorService;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 28.10.2021
 * Реализация сервиса авторов
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Создать автора
     *
     * @param author автор
     * @return автор
     */
    @Override
    @Transactional
    public Author create(Author author) {
        return authorRepository.create(author);
    }

    /**
     * Редактировать автора
     *
     * @param author Автор
     * @return Автор
     */
    @Override
    @Transactional
    public Author update(Author author) {
        return authorRepository.update(author);
    }

    /**
     * Получить автора по ид
     *
     * @param id ид
     * @return автор
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    /**
     * Удалить автора
     *
     * @param id ид
     */
    @Override
    @Transactional
    public void deleteById(Long id) {
        findById(id).ifPresent(authorRepository::delete);
    }

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    @Override
    @Transactional(readOnly = true)
    public List<Author> findByAll() {
        return authorRepository.findByAll();
    }

    /**
     * Поиск авторов по фио
     *
     * @param fio фио автора
     * @return список авторов
     */
    @Override
    @Transactional(readOnly = true)
    public List<Author> findByFio(String fio) {
        return authorRepository.findByFio(fio);
    }
}
