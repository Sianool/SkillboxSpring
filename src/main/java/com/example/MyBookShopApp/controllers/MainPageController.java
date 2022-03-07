package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.struct.book.Book;
import com.example.MyBookShopApp.data.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;


@Controller
public class MainPageController {

    private final BookService bookService;

    @Autowired
    public MainPageController(BookService bookService) {
        this.bookService = bookService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getBookData();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks(){
        return bookService.getBookData();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return bookService.getBookData();
    }

    @GetMapping("/")
    public String mainPage() {
        return "index";
    }

    @GetMapping("/documents/index")
    public String documentsPage() {
        return "documents/index";
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "about";
    }

    @GetMapping("/faq")
    public String faqPage() {
        return "faq";
    }

    @GetMapping("/contacts")
    public String contactsPage() {
        return "contacts";
    }

    @GetMapping("/search/*")
    public String searchPage() {
        return "/search/index";
    }

}
