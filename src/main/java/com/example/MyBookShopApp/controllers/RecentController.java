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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
public class RecentController extends SearchController{

    private final BookService bookService;

    @Autowired
    public RecentController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/recent")
    public String recentBooksPage() {
        return "/books/recent";
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks(){
        return bookService.getPageOfRecentBooks(0,10).getContent();
    }

    @GetMapping("/books/recent")
    @ResponseBody
    public BooksPageDto getRecentBooksPage(@RequestParam(value = "from", required = false, defaultValue = "01.01.1900") String from,
                                           @RequestParam(value = "to", required = false, defaultValue = "NOW") String to,
                                           @RequestParam("offset") Integer offset,
                                           @RequestParam("limit") Integer limit) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        if (to.equals("NOW")) to = LocalDate.now().format(formatter);
        LocalDate fromDate = LocalDate.parse(from, formatter);
        LocalDate toDate = LocalDate.parse(to, formatter);
        return new BooksPageDto(bookService.getPageOfRecentBooksPubDateBetween(fromDate, toDate, offset, limit).getContent());
    }
}
