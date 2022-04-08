package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.services.GenreService;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.dto.GenresDto;
import com.example.MyBookShopApp.struct.book.Book;
import com.example.MyBookShopApp.struct.genre.GenreEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class GenreController extends SearchController{

    private final GenreService genreService;

    public GenreController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/genres")
    public String genresPage() {
        return "genres/index";
    }

    @ModelAttribute("genres")
    public List<GenresDto> genres() {
        return genreService.getGenresData();
    }

    @GetMapping("/genres/{slug}")
    public String genrePage(@PathVariable("slug") String slug, Model model) {
        model.addAttribute("pageSlug", slug);
        model.addAttribute("bookList", genreService.getPageOfGenreBooks(slug, 0, 10).getContent());
        model.addAttribute("genre", genreService.getGenreBySlug(slug));
        return "/genres/slug";
    }

    @GetMapping("/books/genre/{slug}")
    @ResponseBody
    public BooksPageDto newGenrePage(@RequestParam("offset") Integer offset,
                                     @RequestParam("limit") Integer limit,
                                     @PathVariable(value = "slug") String slug) {
        List<Book> content = genreService.getPageOfGenreBooks(slug, offset, limit).getContent();
        return new BooksPageDto(content);
    }
}
