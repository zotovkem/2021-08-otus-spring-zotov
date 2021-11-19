package ru.zotov.hw8.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw8.dao.AuthorRepository;
import ru.zotov.hw8.domain.Author;
import ru.zotov.hw8.service.AuthorService;

import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * @author Created by ZotovES on 28.10.2021
 * Реализация сервиса авторов
 */
@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository authorRepository;

    /**
     * Сохранить автора
     *
     * @param author Автор
     * @return Автор
     */
    @Override
    public Author save(Author author) {
        return authorRepository.save(author);
    }

    /**
     * Получить автора по ид
     *
     * @param id ид
     * @return автор
     */
    @Override
    public Optional<Author> findById(String id) {
        return authorRepository.findById(id);
    }

    /**
     * Удалить автора
     *
     * @param id ид
     */
    @Override
    @Transactional
    public void deleteById(String id) {
        authorRepository.deleteWithConstraintsById(id);
    }

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    @Override
    public List<Author> findByAll() {
        return authorRepository.findAll();
    }

    /**
     * Поиск авторов по фио
     *
     * @param fio фио автора
     * @return список авторов
     */
    @Override
    public List<Author> findByFio(String fio) {
        return authorRepository.findByFio(fio);
    }

    /**
     * Поиск авторов по списку ид
     *
     * @param ids список ид
     * @return список авторов
     */
    @Override
    public Set<Author> findByIdIn(List<String> ids) {
        return authorRepository.findByIdIn(ids);
    }
}
