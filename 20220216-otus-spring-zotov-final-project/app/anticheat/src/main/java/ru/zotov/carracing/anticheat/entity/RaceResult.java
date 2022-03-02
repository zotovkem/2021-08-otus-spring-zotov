package ru.zotov.carracing.anticheat.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Created by ZotovES on 03.09.2021
 * Результат заезда
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "race_result", schema = "anticheat_schema")
public class RaceResult {
    /**
     * Ид
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Ид профиля игрока
     */
    @Column(name = "profile_id")
    private UUID profileId;
    /**
     * Внешний ид заезда
     */
    @Column(name = "external_race_id")
    private UUID externalRaceId;
    /**
     * Время старта заезда
     */
    @Column(name = "race_start_time")
    private Long raceStartTime;
    /**
     * Время финиша заезда
     */
    @Nullable
    @Column(name = "race_finish_time")
    private Long raceFinishTime;
    /**
     * Ид награды
     */
    @Column(name = "reward_id")
    private Long rewardId;
    /**
     * Признак успешной проверки на читерство
     */
    @Column(name = "race_valid")
    private Boolean raceValid;
    /**
     * Причина не успешной проверки
     */
    @Column(name = "description")
    private String description;


}
