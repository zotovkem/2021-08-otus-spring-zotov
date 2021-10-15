package ru.zotov.hw5.dao.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.zotov.hw5.domain.Book;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * @author Created by ZotovES on 06.10.2021
 * Маппер книг
 */
@Component
public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Book(rs.getLong("id"), rs.getString("name"), rs.getInt("release_year"), List.of(), List.of());
    }
}
