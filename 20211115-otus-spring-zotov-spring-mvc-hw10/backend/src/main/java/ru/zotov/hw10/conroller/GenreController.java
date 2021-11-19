package ru.zotov.hw10.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw10.domain.Genre;
import ru.zotov.hw10.service.GenreService;

import java.util.List;

/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер  жанров
 */
@RestController
@RequestMapping(value = "/api/genres")
@RequiredArgsConstructor
public class GenreController {
    private final GenreService genreService;

    /**
     * Создать жанр
     *
     * @param genre жанр
     */
    @PostMapping
    public Genre addGenre(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    /**
     * Найти жанр по ид
     *
     * @param id ид
     * @return жанр
     */
    @GetMapping("/{id}")
    public Genre getById(@PathVariable("id") String id) {
        return genreService.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found book by id = " + id));
    }

    /**
     * Получить список всех жанров
     *
     * @return список жанров
     */
    @GetMapping
    public List<Genre> getAll() {
        return genreService.findAll();
    }

    /**
     * Редактировать жанр
     * Пример genre-update -id 1 -name Повесть
     *
     * @param genre жанр
     * @return жанр
     */
    @PutMapping
    public Genre update(@RequestBody Genre genre) {
        return genreService.save(genre);
    }

    /**
     * Удалить жанры по списку ид
     *
     * @param ids список ид жанра
     */
    @DeleteMapping
    public void deleteByListIds(@RequestBody List<String> ids) {
        genreService.deleteByListIds(ids);
    }
}
