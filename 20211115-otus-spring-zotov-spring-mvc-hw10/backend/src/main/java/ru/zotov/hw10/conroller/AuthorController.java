package ru.zotov.hw10.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw10.domain.Author;
import ru.zotov.hw10.service.AuthorService;

import java.util.List;


/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер управления авторами
 */
@RestController
@RequestMapping(value = "/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;

    /**
     * Создать автора
     *
     * @param author автор
     */
    @PostMapping
    public void create(@RequestBody Author author) {
        authorService.save(new Author(null, author.getFio()));
    }

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    @GetMapping
    public List<Author> getAll() {
        return authorService.findByAll();
    }

    /**
     * Найти автора по ид
     *
     * @param id ид
     * @return автор
     */
    @GetMapping("/{id}")
    public Author getById(@PathVariable(value = "id") String id) {
        return authorService.findById(id).orElseThrow(() -> new IllegalArgumentException("Not found author by id = " + id));
    }

    /**
     * Редактировать автора
     *
     * @param author автор
     * @return автор
     */
    @PutMapping
    public Author update(@RequestBody Author author) {
        return authorService.save(author);
    }

    /**
     * Удалить авторов по списку ид
     *
     * @param ids ид
     */
    @DeleteMapping()
    public void deleteByListIds(@RequestParam("ids") List<String> ids) {
        authorService.deleteByListIds(ids);
    }
}
