package com.example.MyBookShopApp.data;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.*;

@Service
public class BookService {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> getBookData() {
        List<Book> books = jdbcTemplate.query("SELECT * FROM books", (ResultSet rs, int rowNums) -> {

            Book book = new Book();
            book.setId(rs.getInt("id"));
            book.setAuthor(rs.getString("author"));
            book.setTitle(rs.getString("title"));
            book.setPriceOld(rs.getString("priceOld"));
            book.setPrice(rs.getString("price"));
            int priceOld = (int) (Double.parseDouble(book.getPriceOld().substring(1))*100);
            int price = (int) (Double.parseDouble(book.getPrice().substring(1))*100);
            book.setDiscount((priceOld - price)*100 / priceOld);
            if (book.getDiscount() > 0) book.setSale(true);
            return book;
        });
        return new ArrayList<>(books);
    }


}
