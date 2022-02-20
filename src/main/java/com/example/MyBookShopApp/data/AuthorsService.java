package com.example.MyBookShopApp.data;


import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class AuthorsService {

    private final JdbcTemplate jdbcTemplate;

    public AuthorsService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Map<String, List<Author>> getAuthorMap() {
        List<Author> authors = jdbcTemplate.query("SELECT * FROM authors ORDER BY last_name, first_name", (ResultSet rs, int rowNums) -> {
            Author author = new Author();
            author.setId(rs.getInt("id"));
            author.setFirstName(rs.getString("first_name"));
            author.setLastName(rs.getString("last_name"));
            return author;
        });

        return authors.stream().collect(Collectors.groupingBy((Author a) -> a.getLastName().substring(0,1)));
    }
}
