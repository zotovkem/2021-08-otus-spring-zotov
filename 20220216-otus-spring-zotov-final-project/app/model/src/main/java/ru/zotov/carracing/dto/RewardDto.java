package ru.zotov.carracing.dto;

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
public class RewardDto {
    private Long id;
    private RewardType rewardType;
    private String name;
    private Integer total;
}
