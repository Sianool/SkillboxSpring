package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.struct.Author;
import com.example.MyBookShopApp.data.services.AuthorService;
import com.example.MyBookShopApp.struct.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class AuthorController extends SearchController{

    private final AuthorService authorService;

    @Autowired
    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ModelAttribute("authorsMap")
    public Map<String, List<Author>> authorMap() {
        return authorService.getAuthorMap();
    }

    @ModelAttribute("slug")
    public SearchWordDto searchAuth() {
        return new SearchWordDto();
    }

    @GetMapping("/authors")
    public String authorsPage() {
        return "/authors/index";
    }

    @GetMapping("/authors/{slug}")
    public String getAuthorPage(@PathVariable("slug") String slug, Model model) {
        getAuthorBooks(slug, model);
        return "/authors/slug";
    }

    @GetMapping("/books/author/{slug}")
    public String getAuthorBooksPage(@PathVariable("slug") String slug, Model model) {
        getAuthorBooks(slug, model);
        return "/books/author";
    }

    @GetMapping("/newPage/author/{slug}")
    @ResponseBody
    public BooksPageDto getNewAuthorBooksPage(@RequestParam("offset") Integer offset,
                                              @RequestParam("limit") Integer limit,
                                              @PathVariable(value = "slug") String slug) {
        Author authorBySlug = authorService.getAuthorBySlug(slug);
        List<Book> content = authorService.getPageOfAuthorBooks(authorBySlug.getId(), offset, limit).getContent();
        return new BooksPageDto(content);
    }

    private void getAuthorBooks(String slug, Model model) {
        Author authorBySlug = authorService.getAuthorBySlug(slug);
        List<Book> content = authorService.getPageOfAuthorBooks(authorBySlug.getId(), 0, 10).getContent();
        model.addAttribute("pageSlug", slug);
        model.addAttribute("bookList", content);
        model.addAttribute("author", authorBySlug);
    }
}
