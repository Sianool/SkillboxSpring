package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.struct.book.Book;
import com.example.MyBookShopApp.data.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PopularController extends SearchController{

    private final BookService bookService;

    @Autowired
    public PopularController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/popular")
    public String popularBooksPage() {
        return "/books/popular";
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return bookService.getPageOfPopularBooks(0,10).getContent();
    }

    @GetMapping("/books/popular")
    @ResponseBody
    public BooksPageDto getPopularBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfPopularBooks(offset, limit).getContent());
    }
}
