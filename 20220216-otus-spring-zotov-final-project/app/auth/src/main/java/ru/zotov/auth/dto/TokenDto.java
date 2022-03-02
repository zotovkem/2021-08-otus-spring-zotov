package ru.zotov.auth.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 08.09.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "TokenDto: Токен")
public class TokenDto {
    @ApiModelProperty(value = "Рефреш токен", example = "dfthtrh")
    private String refreshToken;
    @ApiModelProperty(value = "Токен", example = "dfgdfgdfg")
    private String token;
}
