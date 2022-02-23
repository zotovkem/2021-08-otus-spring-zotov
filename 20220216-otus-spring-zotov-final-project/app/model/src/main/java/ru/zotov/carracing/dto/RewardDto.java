package ru.zotov.carracing.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zotov.carracing.enums.RewardType;

/**
 * @author Created by ZotovES on 22.02.2022
 * Дто Награды
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "RewardDto: Награда ")
public class RewardDto {
    @ApiModelProperty(value = "Ид", example = "1")
    private Long id;
    @ApiModelProperty(value = "Тип награды", example = "CAR", allowableValues = "MONEY, CAR, FUEL")
    private RewardType rewardType;
    @ApiModelProperty(value = "Наименование награды", example = "Уникальное авто")
    private String name;
    @ApiModelProperty(value = "Количество", example = "1")
    private Integer total;
}
