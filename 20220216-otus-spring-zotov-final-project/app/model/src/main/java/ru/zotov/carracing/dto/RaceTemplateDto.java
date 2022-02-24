package ru.zotov.carracing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Created by ZotovES on 10.08.2021
 * Дто заезда
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RaceTemplateDto: Шаблон заезда ")
public class RaceTemplateDto {
    @ApiModelProperty(value = "Ид", example = "1")
    private Long id;
    @ApiModelProperty(value = "Внешний ид награды", example = "40d1e5f9-0dcb-44db-8de7-86104d8b3928")
    private Long rewardId;
    @ApiModelProperty(value = "Наименование команды", example = "Награда за заезд")
    private String name;
    @ApiModelProperty(value = "Ид трассы", example = "1")
    private Integer trackId;
}
