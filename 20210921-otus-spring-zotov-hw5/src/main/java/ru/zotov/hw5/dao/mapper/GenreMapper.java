package ru.zotov.hw5.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zotov.hw5.domain.Genre;

import java.sql.ResultSet;
import java.sql.SQLException;


/**
 * @author Created by ZotovES on 06.10.2021
 * Маппер жанров
 */
public class GenreMapper implements RowMapper<Genre> {
    @Override
    public Genre mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Genre(rs.getLong("id"), rs.getString("name"));
    }
}
