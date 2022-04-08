package com.example.MyBookShopApp.controllers.Api;

import com.example.MyBookShopApp.data.services.AuthorService;
import com.example.MyBookShopApp.data.dto.BooksPageDto;
import com.example.MyBookShopApp.struct.Author;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Api(description = "authors data")
public class AuthorApiController {

    private final AuthorService authorService;

    public AuthorApiController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @ApiOperation("method to get map of authors")
    @GetMapping("/authors")
    @ResponseBody
    public Map<String, List<Author>> authors() {
        return authorService.getAuthorMap();
    }

    @ApiOperation("method to get books of author")
    @GetMapping("/books/author/{Id}")
    @ResponseBody
    public BooksPageDto authorBooks(@PathVariable("Id") Integer id) {
        return new BooksPageDto(authorService.getPageOfAuthorBooks(id,0,100).getContent());
    }
}
