package com.example.MyBookShopApp.controllers;


import com.example.MyBookShopApp.data.dto.SearchWordDto;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.data.services.TagService;
import com.example.MyBookShopApp.errs.EmptySearchException;
import com.example.MyBookShopApp.struct.book.Book;
import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.struct.genre.TagEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class MainPageController extends SearchController{

    private final BookService bookService;
    private final TagService tagService;

    @Autowired
    public MainPageController(BookService bookService, TagService tagService) {
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @ModelAttribute("recommendedBooks")
    public List<Book> recommendedBooks() {
        return bookService.getPageOfRecommendedBooks(0,6).getContent();
    }

    @ModelAttribute("recentBooks")
    public List<Book> recentBooks(){
        return bookService.getPageOfRecentBooks(0,6).getContent();
    }

    @ModelAttribute("popularBooks")
    public List<Book> popularBooks(){
        return bookService.getPageOfPopularBooks(0,6).getContent();
    }

    @ModelAttribute("tagList")
    public List<TagEntity> tagList(){
        return tagService.getTagData();
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

    @GetMapping("/books/recommended")
    @ResponseBody
    public BooksPageDto getRecommendedBooksPage(@RequestParam("offset") Integer offset, @RequestParam("limit") Integer limit) {
        return new BooksPageDto(bookService.getPageOfRecommendedBooks(offset, limit).getContent());
    }

    @GetMapping(value = {"/search", "/search/{searchWord}"})
    public String getSearchResults(@PathVariable(value = "searchWord", required = false)
                                           SearchWordDto searchWordDto, Model model) throws EmptySearchException {
        if(searchWordDto != null) {
            Page<Book> pageOfSearchResultBooks = bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), 0, 10);
            model.addAttribute("searchWordDto", searchWordDto);
            model.addAttribute("searchResultSize", pageOfSearchResultBooks.getTotalElements());
            model.addAttribute("searchResults", pageOfSearchResultBooks.getContent());
            return "/search/index";
        } else {
            throw new EmptySearchException("Поиск по null невозможен");
        }

    }

    @GetMapping("search/page/{searchWord}")
    @ResponseBody
    public BooksPageDto getNextSearchPage(@RequestParam("offset") Integer offset,
                                          @RequestParam("limit") Integer limit,
                                          @PathVariable(value = "searchWord", required = false) SearchWordDto searchWordDto) {
        return new BooksPageDto(bookService.getPageOfSearchResultBooks(searchWordDto.getExample(), offset, limit).getContent());
    }
}
