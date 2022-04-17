package com.example.MyBookShopApp.controllers.Api;


import com.example.MyBookShopApp.data.ApiResponse;
import com.example.MyBookShopApp.data.services.BookService;
import com.example.MyBookShopApp.errs.BookstoreApiWrongParameterException;
import com.example.MyBookShopApp.struct.book.Book;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
@Api(description = "book data api")
public class BooksRestApiController {

    private final BookService bookService;


    @Autowired
    public BooksRestApiController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/books/by-author")
    @ApiOperation("operation to get book list of bookshop by passed author first name")
    public ResponseEntity<List<Book>> booksByAuthor(@RequestParam("author_id")Integer id){
        return ResponseEntity.ok(bookService.getBooksByAuthorId(id));
    }

    @GetMapping("/books/by-title")
    @ApiOperation("get books by title")
    public ResponseEntity<ApiResponse<Book>> booksByTitle(@RequestParam("title")String title) throws BookstoreApiWrongParameterException {
        ApiResponse<Book> response = new ApiResponse<>();
        List<Book> data = bookService.getBooksByTitle(title);
        response.setDebugMessage("successful request");
        response.setMessage("data size: " + data.size() + " elements");
        response.setHttpStatus(HttpStatus.OK);
        response.setTimeStamp(LocalDateTime.now());
        response.setData(data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/books/by-price-range")
    @ApiOperation("get books by price range from min price to max price")
    public ResponseEntity<List<Book>> priceRangeBooks(@RequestParam("min")Integer min, @RequestParam("max")Integer max){
        return ResponseEntity.ok(bookService.getBooksWithPriceBetween(min, max));
    }

    @GetMapping("/books/with-max-discount")
    @ApiOperation("get list of book with max discount")
    public ResponseEntity<List<Book>> maxPriceBooks(){
        return ResponseEntity.ok(bookService.getBooksWithMaxDiscount());
    }

    @GetMapping("/books/bestsellers")
    @ApiOperation("get bestseller book (which is_bestseller = 1)")
    public ResponseEntity<List<Book>> bestSellerBooks(){
        return ResponseEntity.ok(bookService.getBestSellers());
    }

    @GetMapping("/books/recent-books")
    @ApiOperation("list books ordered by date published")
    public ResponseEntity<List<Book>> recentBooks() {
        return ResponseEntity.ok(bookService.getRecentBookData());
    }

    @GetMapping("/books/by-genre")
    @ApiOperation("get books by genre")
    public ResponseEntity<List<Book>> booksByGenre(@RequestParam("genre")Integer id){
        return ResponseEntity.ok(bookService.getBooksByGenre(id));
    }

    @GetMapping("/books/popular")
    @ApiOperation("get books order by popularity")
    public ResponseEntity<List<Book>> popularBooks(){
        return ResponseEntity.ok(bookService.getPageOfPopularBooks(0, 100).getContent());
    }

    @GetMapping("/books/recommended")
    @ApiOperation("get books order by rating")
    public ResponseEntity<List<Book>> recommendedBooks(){
        return ResponseEntity.ok(bookService.getPageOfRecommendedBooks(0, 100).getContent());
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleMissingServletRequestParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Missing required parameters",
                exception), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookstoreApiWrongParameterException.class)
    public ResponseEntity<ApiResponse<Book>> handleBookstoreApiWrongParameterException(Exception exception) {
        return new ResponseEntity<>(new ApiResponse<Book>(HttpStatus.BAD_REQUEST, "Bad parameter value",
                exception), HttpStatus.BAD_REQUEST);
    }
}

