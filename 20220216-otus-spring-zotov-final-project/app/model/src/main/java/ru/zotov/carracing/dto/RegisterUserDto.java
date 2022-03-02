package ru.zotov.carracing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 28.08.2021
 */
@Data
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@ApiModel(value = "RegisterUserDto: Информация о регистрации пользователя ")
public class RegisterUserDto {
    @ApiModelProperty(value = "Ид", example = "1")
    private Long id;
    @ApiModelProperty(value = "Ник игрока", example = "Joni")
    private String nickname;
    @ApiModelProperty(value = "Внешний ид профиля", example = "ace942a1-220c-4b25-9fda-bf580f2bfc8f")
    private UUID profileId;
    @ApiModelProperty(value = "Почта", example = "test@mail.ru")
    private String email;
}
