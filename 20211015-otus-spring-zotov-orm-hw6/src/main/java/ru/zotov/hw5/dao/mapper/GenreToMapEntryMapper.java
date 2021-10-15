package ru.zotov.hw5.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

/**
 * @author Created by ZotovES on 06.10.2021
 * Маппер жанра в Map.Entry
 */
@Component
public class GenreToMapEntryMapper implements RowMapper<Map.Entry<Long, Long>> {
    @Override
    public Map.Entry<Long, Long> mapRow(ResultSet rs, int rowNum) throws SQLException {
        return Map.entry(rs.getLong("book_id"), rs.getLong("genre_id"));
    }
}
