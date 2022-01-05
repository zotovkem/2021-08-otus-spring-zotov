package ru.zotov.hw14.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.zotov.hw14.constant.MigrationTableName;
import ru.zotov.hw14.domain.MigrationRegistry;

import java.util.List;
import java.util.Optional;

/**
 * @author Created by ZotovES on 02.01.2022
 * Репозиторий перенесенных записей
 */
public interface MigrationRegistryRepository extends JpaRepository<MigrationRegistry,Long> {
    /**
     * Поиск всех записей по имени таблицы
     * @param tableName имя таблицы
     * @return список записей
     */
    List<MigrationRegistry> findByTableName(String tableName);

    /**
     * Поиск записи по внешнему ид
     * @param externalId внешний ид записи
     * @param tableName имя таблицы
     * @return перенесенная запись
     */
    Optional<MigrationRegistry> findByExternalIdAndTableName(String externalId, MigrationTableName tableName);
}
