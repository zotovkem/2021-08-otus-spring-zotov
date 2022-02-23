package ru.zotov.carracing.profile.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.zotov.carracing.common.mapper.Mapper;
import ru.zotov.carracing.profile.config.SpringfoxConfig;
import ru.zotov.carracing.profile.dto.ProfileDto;
import ru.zotov.carracing.profile.service.ProfileService;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * @author Created by ZotovES on 14.09.2021
 */
@RestController
@RequiredArgsConstructor
@Api(value = "Profile", tags = {SpringfoxConfig.PROFILE})
@RequestMapping(value = "profiles", produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
public class ProfileController {
    private final Mapper mapper;
    private final ProfileService profileService;

    @ApiOperation("Получить профиль текущего игрока ")
    @GetMapping("/current")
    public ResponseEntity<ProfileDto> getCurrentProfile() {

        return profileService.getCurrentProfile()
                .map(p -> mapper.map(p, ProfileDto.class))
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
