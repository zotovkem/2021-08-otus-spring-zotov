package ru.zotov.hw13.conroller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw13.domain.Genre;
import ru.zotov.hw13.dto.GenreDto;
import ru.zotov.hw13.service.GenreService;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер  жанров
 */
@RestController
@RequestMapping(value = "/api/genres")
@RequiredArgsConstructor
public class GenreController {
    private final ModelMapper mapper;
    private final GenreService genreService;

    /**
     * Создать жанр
     *
     * @param genreDto dto жанра
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GenreDto addGenre(@RequestBody GenreDto genreDto) {
        return mapper.map(genreService.save(new Genre(null, genreDto.getName())), GenreDto.class);
    }

    /**
     * Найти жанр по ид
     *
     * @param id ид
     * @return жанр
     */
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getById(@PathVariable("id") String id) {
        return genreService.findById(id)
                .map(genre -> mapper.map(genre, GenreDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Получить список всех жанров
     *
     * @return список жанров
     */
    @GetMapping
    public List<GenreDto> getAll() {
        return genreService.findAll().stream()
                .map(genre -> mapper.map(genre, GenreDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Редактировать жанр
     *
     * @param genreDto dto жанра
     * @return жанр
     */
    @PutMapping
    public GenreDto update(@RequestBody GenreDto genreDto) {
        Genre genre = mapper.map(genreDto, Genre.class);
        return mapper.map(genreService.save(genre), GenreDto.class);
    }

    /**
     * Удалить жанры по списку ид
     *
     * @param ids список ид жанра
     */
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<?> deleteByListIds(@RequestBody List<String> ids) {
        try {
            genreService.deleteByListIds(ids);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
