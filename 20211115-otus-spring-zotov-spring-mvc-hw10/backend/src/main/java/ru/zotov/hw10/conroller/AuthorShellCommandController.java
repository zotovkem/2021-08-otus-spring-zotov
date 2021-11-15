package ru.zotov.hw10.conroller;

import lombok.RequiredArgsConstructor;
import ru.zotov.hw10.domain.Author;
import ru.zotov.hw10.service.AuthorService;

import java.util.List;

/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер команд управления авторами
 */
@SuppressWarnings("unused")
@RequiredArgsConstructor
public class AuthorShellCommandController {
    private final AuthorService authorService;

    /**
     * Создать автора
     *
     * @param fio фио автора
     */
//    @ShellMethod(value = "Create author", key = {"author-add"})
    public void create(@ShellOption("-fio") String fio) {
        authorService.save(new Author(null, fio));
    }

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
//    @ShellMethod(value = "Get all authors", key = {"author-get-all"})
    public List<Author> getAll() {
        return authorService.findByAll();
    }

    /**
     * Найти автора по ид
     *
     * @param id ид
     * @return автор
     */
//    @ShellMethod(value = "Find author by id", key = {"author-find-by-id"})
    public Author getById(String id) {
        return authorService.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found author by id = " + id));
    }

    /**
     * Редактировать автора
     * <p>
     * Пример author-update -id 9 -fio Зотов
     *
     * @param id  ид
     * @param fio фио автора
     * @return автор
     */
//    @ShellMethod(value = "Update author", key = {"author-update"})
    public Author update(@ShellOption("-id") String id, @ShellOption("-fio") String fio) {
        return authorService.save(new Author(id, fio));
    }

    /**
     * Удалить автора
     *
     * @param id ид
     */
//    @ShellMethod(value = "Delete author by id", key = {"author-delete-by-id"})
    public void deleteById(@ShellOption("-id") String id) {
        authorService.deleteById(id);
    }
}
