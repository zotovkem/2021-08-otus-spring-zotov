package ru.zotov.carracing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import ru.zotov.carracing.enums.RaceState;

import javax.persistence.*;
import java.util.UUID;

/**
 * @author Created by ZotovES on 18.08.2021
 * Заезд
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "race", schema = "race_schema")
public class Race {
    /**
     * Ид
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Внешний ид
     */
    @Nullable
    @Column(name = "external_id")
    private UUID externalId;
    /**
     * Внешний ид профиля игрока
     */
    @Column(name = "profile_Id")
    private UUID profileId;
    /**
     * Шаблон заезда
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "race_template_id", referencedColumnName = "id")
    private RaceTemplate raceTemplate;
    /**
     * Статус звезда
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private RaceState state;
    /**
     * Время старта заезда
     */
    @Nullable
    @Column(name = "race_start_time")
    private Long raceStartTime;
}
