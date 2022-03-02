package ru.zotov.carracing.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.zotov.carracing.enums.RewardType;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 10.08.2021
 * Награда
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "reward", schema = "dictionary_schema")
public class Reward {
    /**
     * Ид
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Тип награды
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private RewardType rewardType;
    /**
     * Наименование
     */
    @Column(name = "name")
    private String name;
    /**
     * Кол-во
     */
    @Column(name = "total")
    private Integer total;
}
