package ru.zotov.hw10.conroller;

import lombok.RequiredArgsConstructor;
import ru.zotov.hw10.service.GenreService;

/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер  жанров
 */
@RequiredArgsConstructor
public class GenreShellCommandController {
    private final GenreService genreService;
//
//    /**
//     * Создать жанр
//     * Пример genre-add -name Повесть
//     *
//     * @param name наименование жанра
//     */
//    @ShellMethod(value = "Create genre", key = {"genre-add"})
//    public void addGenre(@ShellOption({"-name"}) String name) {
//        genreService.save(new Genre(null, name));
//    }
//
//    /**
//     * Найти жанр по ид
//     *
//     * @param id ид
//     * @return жанр
//     */
//    @ShellMethod(value = "Find genres by list id", key = {"genre-find-by-id"})
//    public Genre getById(String id) {
//        return genreService.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found book by id = " + id));
//    }
//
//    /**
//     * Получить список всех жанров
//     *
//     * @return список жанров
//     */
//    @ShellMethod(value = "Get all genres ", key = {"genre-get-all"})
//    public List<Genre> getAll() {
//        return genreService.findAll();
//    }
//
//    /**
//     * Редактировать жанр
//     * Пример genre-update -id 1 -name Повесть
//     *
//     * @param id   ид
//     * @param name наименование жанра
//     * @return жанр
//     */
//    @ShellMethod(value = "Update genre", key = "genre-update")
//    public Genre update(@ShellOption({"-id"}) String id, @ShellOption({"-name"}) String name) {
//        return genreService.save(new Genre(id, name));
//    }
//
//    /**
//     * Удалить жанр
//     *
//     * @param id ид жанра
//     */
//    @ShellMethod(value = "Delete genre by id", key = {"genre-delete-by-id"})
//    public void deleteById(String id) {
//        genreService.deleteById(id);
//    }
}
