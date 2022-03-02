package ru.zotov.carracing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 07.09.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RegisterUserDto: Информация о регистрации пользователя ")
public class LoginDto {
    @ApiModelProperty(value = "Почта", example = "test@mail.ru")
    private String email;
    @ApiModelProperty(value = "Пароль", example = "password")
    private String password;
}
