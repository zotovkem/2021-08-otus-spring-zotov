package ru.zotov.hw18.conroller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zotov.hw18.domain.Author;
import ru.zotov.hw18.dto.AuthorDto;
import ru.zotov.hw18.service.AuthorService;

import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Created by ZotovES on 08.10.2021
 * Контроллер управления авторами
 */
@RestController
@RequestMapping(value = "/api/authors")
@RequiredArgsConstructor
public class AuthorController {
    private final ModelMapper mapper;
    private final AuthorService authorService;

    /**
     * Создать автора
     *
     * @param authorDto автор
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AuthorDto create(@RequestBody AuthorDto authorDto) {
        return mapper.map(authorService.save(new Author(null, authorDto.getFio())), AuthorDto.class);
    }

    /**
     * Получить всех авторов
     *
     * @return список авторов
     */
    @GetMapping
    public List<AuthorDto> getAll() {
        return authorService.findByAll().stream()
                .map(author -> mapper.map(author, AuthorDto.class))
                .collect(Collectors.toList());
    }

    /**
     * Найти автора по ид
     *
     * @param id ид
     * @return автор
     */
    @GetMapping("/{id}")
    public ResponseEntity<AuthorDto> getById(@PathVariable(value = "id") Long id) {
        return authorService.findById(id)
                .map(author -> mapper.map(author, AuthorDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Редактировать автора
     *
     * @param authorDto автор
     * @return автор
     */
    @PutMapping
    public AuthorDto update(@RequestBody AuthorDto authorDto) {
        Author author = mapper.map(authorDto, Author.class);
        return mapper.map(authorService.save(author), AuthorDto.class);
    }

    /**
     * Удалить авторов по списку ид
     *
     * @param ids ид
     */
    @DeleteMapping
    public ResponseEntity<?> deleteByListIds(@RequestBody List<Long> ids) {
        try {
            authorService.deleteByListIds(ids);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
}
