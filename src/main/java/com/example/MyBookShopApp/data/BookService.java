package com.example.MyBookShopApp.data;


import com.example.MyBookShopApp.struct.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<Book> getBookData() {
        List<Book> books = bookRepository.findAll();
        for (Book book : books) {
            int priceOld = Integer.parseInt(book.getPriceOld());
            int price = Integer.parseInt(book.getPrice());
            book.setDiscount((priceOld  - price)*100 / priceOld);
            if (book.getDiscount() > 0) book.setSale(true);
        }
        return books;
    }


}
