package ru.zotov.hw14.domain;

import lombok.*;
import ru.zotov.hw14.constant.MigrationTableName;

import javax.persistence.*;

/**
 * @author Created by ZotovES on 02.01.2022
 * Журнал миграции записей из монго в jpa
 */
@Data
@NoArgsConstructor
@AllArgsConstructor

@Entity
@Table(name = "migration_registry")
public class MigrationRegistry {
    /**
     * Ид
     */
    @Id
    @Column(name = "id", unique = true)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Ид импортированной записи
     */
    @Column(name = "entity_id")
    private Long entityId;

    /**
     * Внещний ид
     */
    @Column(name = "external_id")
    private String externalId;

    /**
     * Имя таблицы
     */
    @Column(name = "table_name")
    @Enumerated(EnumType.STRING)
    private MigrationTableName tableName;
}
