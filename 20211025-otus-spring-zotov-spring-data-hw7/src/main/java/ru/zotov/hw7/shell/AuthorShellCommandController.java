package ru.zotov.hw7.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.zotov.hw7.dao.AuthorRepository;
import ru.zotov.hw7.domain.Author;

import java.util.List;

/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер команд управления авторами
 */
@SuppressWarnings("unused")
@ShellComponent
@RequiredArgsConstructor
public class AuthorShellCommandController {
    private final AuthorRepository authorDao;

    /**
     * Создать автора
     * Пример author-add -fio Зотов
     *
     * @param fio фио автора
     */
    @ShellMethod(value = "Create author", key = {"author-add"})
    public void create(@ShellOption("-fio") String fio) {
        authorDao.save(new Author(null, fio));
    }

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    @ShellMethod(value = "Get all authors", key = {"author-get-all"})
    public List<Author> getAll() {
        return authorDao.findAll();
    }

    /**
     * Найти автора по ид
     *
     * @param id ид
     * @return автор
     */
    @ShellMethod(value = "Find author by id", key = {"author-find-by-id"})
    public Author getById(Long id) {
        return authorDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found author by id = " + id));
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
    @ShellMethod(value = "Update author", key = {"author-update"})
    public Author update(@ShellOption("-id") Long id, @ShellOption("-fio") String fio) {
        return authorDao.save(new Author(id, fio));
    }

    /**
     * Удалить автора
     *
     * @param id ид
     */
    @ShellMethod(value = "Delete author by id", key = {"author-delete-by-id"})
    public void deleteById(@ShellOption("-id") Long id) {
        authorDao.deleteById(id);
    }
}
