package ru.zotov.hw17.conroller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zotov.hw17.dto.LoginDto;
import ru.zotov.hw17.dto.TokenDto;
import ru.zotov.hw17.service.UserLibraryService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 03.12.2021
 * Контроллер аутентификации
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/auth", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class AuthController {
    private final UserLibraryService userLibraryService;

    @PostMapping(value = "/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginDto loginDto) {
        return userLibraryService.login(loginDto.getUsername(), loginDto.getPassword())
                .map(TokenDto::new)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }
}
