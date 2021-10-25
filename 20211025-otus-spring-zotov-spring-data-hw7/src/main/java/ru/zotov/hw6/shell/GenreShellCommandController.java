package ru.zotov.hw6.shell;

import lombok.RequiredArgsConstructor;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.zotov.hw6.dao.GenreRepository;
import ru.zotov.hw6.domain.Genre;

import java.util.List;

/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер shell команд жанров
 */
@SuppressWarnings("unused")
@ShellComponent
@RequiredArgsConstructor
public class GenreShellCommandController {
    private final GenreRepository genreDao;

    /**
     * Создать жанр
     * Пример genre-add -name Повесть
     *
     * @param name наименование жанра
     */
    @ShellMethod(value = "Create genre", key = {"genre-add"})
    public void getById(@ShellOption({"-name"}) String name) {
        genreDao.create(new Genre(null, name));
    }

    /**
     * Найти жанр по ид
     *
     * @param id ид
     * @return жанр
     */
    @ShellMethod(value = "Find genres by list id", key = {"genre-find-by-id"})
    public Genre getById(Long id) {
        return genreDao.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found book by id = " + id));
    }

    /**
     * Получить список всех жанров
     *
     * @return список жанров
     */
    @ShellMethod(value = "Get all genres ", key = {"genre-get-all"})
    public List<Genre> getAll() {
        return genreDao.findAll();
    }

    /**
     * Редактировать жанр
     *
     * @param id   ид
     * @param name наименование жанра
     * @return жанр
     */
    @ShellMethod(value = "Update genre", key = "genre-update")
    public Genre update(@ShellOption({"-id"}) Long id, @ShellOption({"-name"}) String name) {
        return genreDao.update(new Genre(id, name));
    }

    /**
     * Удалить жанр
     *
     * @param id ид жанра
     */
    @ShellMethod(value = "Delete genre by id", key = {"genre-delete-by-id"})
    public void deleteById(Long id) {
        genreDao.deleteById(id);
    }
}
