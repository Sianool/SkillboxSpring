package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.struct.book.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
public class PopularController {

    private final BookService bookService;

    @Autowired
    public PopularController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return bookService.getBookData();
    }

    @GetMapping("/books/popular")
    public String popularPage() {
        return "books/popular";
    }
}
