package ru.zotov.hw7.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.zotov.hw7.dao.AuthorRepository;
import ru.zotov.hw7.domain.Author;
import ru.zotov.hw7.service.AuthorService;

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
}
