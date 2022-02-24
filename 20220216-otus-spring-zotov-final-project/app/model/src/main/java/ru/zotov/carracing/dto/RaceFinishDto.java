package ru.zotov.carracing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author Created by ZotovES on 17.08.2021
 * Дто финишироавнного заезда
 */
@Data
@ApiModel(value = "RaceFinishDto: Финиш заезда ")
public class RaceFinishDto {
    @ApiModelProperty(value = "Ид", example = "1")
    private Long id;
    @ApiModelProperty(value = "Внешний ид заезда", example = "40d1e5f9-0dcb-44db-8de7-86104d8b3928")
    private String raceExternalId;
}
