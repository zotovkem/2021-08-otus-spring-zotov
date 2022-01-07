package ru.zotov.hw14.constant;

/**
 * @author Created by ZotovES on 05.01.2022
 *
 * Энум с именами таблиц для рееестра миграций
 */
public enum MigrationTableName {
    AUTHOR,
    GENRE,
    BOOK,
    COMMENT;

    public static String[] getTableNameArray() {
        String[] keysArray = new String[MigrationTableName.values().length];
        int i=0;
        for (MigrationTableName tableName : MigrationTableName.values()) {
            keysArray[i++]=tableName.name();
        }
        return keysArray;
    }
}
