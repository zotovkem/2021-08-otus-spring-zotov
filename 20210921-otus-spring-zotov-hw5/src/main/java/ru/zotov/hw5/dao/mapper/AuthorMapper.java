package ru.zotov.hw5.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import ru.zotov.hw5.domain.Author;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Created by ZotovES on 06.10.2021
 * Маппер авторов
 */
public class AuthorMapper implements RowMapper<Author> {
    @Override
    public Author mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Author(rs.getLong("id"), rs.getString("fio"));
    }
}
