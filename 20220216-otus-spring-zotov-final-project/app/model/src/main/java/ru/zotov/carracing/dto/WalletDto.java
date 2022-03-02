package ru.zotov.carracing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * @author Created by ZotovES on 03.09.2021
 * Дто кошлька игрока
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "WalletDto: Дто кошелька игрока  ")
public class WalletDto {
    @ApiModelProperty(value = "Ид", example = "1")
    private Long id;
    @ApiModelProperty(value = "Внешний ид профиля игрока ", example = "17fcd680-dc0e-44a4-8461-edc17b69a1b3")
    private UUID profileId;
    @ApiModelProperty(value = "Количество топлива", example = "100")
    private Integer fuel;
    @ApiModelProperty(value = "Количество денег", example = "3245671")
    private Integer money;
}
