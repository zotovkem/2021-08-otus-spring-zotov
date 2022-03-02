package ru.zotov.carracing.profile.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Created by ZotovES on 14.09.2021
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ProfileDto: Профиль игрока  ")
public class ProfileDto {
     @ApiModelProperty(value = "Ид", example = "1")
    private Long id;
     @ApiModelProperty(value = "Внешний ид", example = "de8c2ad1-9562-4c3c-a89c-0979fcee338b")
    private String externalId;
     @ApiModelProperty(value = "Прогресс игрока", example = "11")
    private Integer progress;
     @ApiModelProperty(value = "Ид текущего авто", example = "1")
    private Integer currentCarId;
     @ApiModelProperty(value = "Очки", example = "123456")
    private Long score;
     @ApiModelProperty(value = "Уровень игрока", example = "5")
    private Integer level;
}
