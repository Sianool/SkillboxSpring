package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.struct.book.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;

@Controller
@RequestMapping("/books")
public class BookshopCookieController extends SearchController{

    @ModelAttribute(name = "bookCart")
    public List<Book> bookCart() {
        return new ArrayList<Book>();
    }

    private final BookRepository bookRepository;

    @Autowired
    public BookshopCookieController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @GetMapping("/cart")
    public String handleCartRequest(@CookieValue(value = "cartContents", required = false) String cartContents,
                                    Model model) {
        handleRequest(cartContents ,"Cart", model);
        return "cart";
    }

    @GetMapping("/postponed")
    public String handlePostponedRequest(@CookieValue(value = "postponedContents", required = false) String postponedContents,
                                    Model model) {
        handleRequest(postponedContents ,"Postponed", model);
        return "postponed";
    }

    @PostMapping("/changeBookStatus/cart/remove/{slug}/UNLINK")
    public String handleRemoveFromCartRequest(@PathVariable("slug") String slug, @CookieValue(name = "cartContents",
            required = false) String cartContents, HttpServletResponse response, Model model) {
        if (cartContents != null && !cartContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(cartContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("cartContents", String.join("/", cookieBooks));
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isCartEmpty", false);
        } else {
            model.addAttribute("isCartEmpty", true);
        }

        return "redirect:/books/cart";
    }

    @PostMapping("/changeBookStatus/postponed/remove/{slug}/UNLINK")
    public String handleRemoveFromPostponedRequest(@PathVariable("slug") String slug, @CookieValue(name = "postponedContents",
            required = false) String postponedContents, HttpServletResponse response, Model model) {
        if (postponedContents != null && !postponedContents.equals("")) {
            ArrayList<String> cookieBooks = new ArrayList<>(Arrays.asList(postponedContents.split("/")));
            cookieBooks.remove(slug);
            Cookie cookie = new Cookie("postponedContents", String.join("/", cookieBooks));
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("isPostponedEmpty", false);
        } else {
            model.addAttribute("isPostponedEmpty", true);
        }

        return "redirect:/books/postponed";
    }

    @PostMapping("/changeBookStatus/{slug}/{status}")
    public String handleChangeBookStatus(@PathVariable("slug") String slug, @PathVariable("status") String status,
                                         @CookieValue(name = "cartContents", required = false) String cartContents,
                                         @CookieValue(name = "postponedContents", required = false) String postponedContents,
                                         HttpServletResponse response, Model model) {

        if (status.equals("CART")) {
            handleRemoveFromPostponedRequest(slug, postponedContents, response, model);
            setCookie(slug, cartContents, "Cart", response, model);
        } else if (status.equals("KEPT")) {
            handleRemoveFromCartRequest(slug, cartContents, response, model);
            setCookie(slug, postponedContents, "Postponed", response, model);
        }
        return "redirect:/books/" + slug;
    }


    private void setCookie(String slug, String contents, String name, HttpServletResponse response, Model model) {
        if (contents == null || contents.equals("")) {
            Cookie cookie = new Cookie(name.toLowerCase() + "Contents", slug);
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("is" +name + "Empty", false);
        }   else if (!contents.contains(slug)) {
            StringJoiner stringJoiner = new StringJoiner("/");
            stringJoiner.add(contents).add(slug);
            Cookie cookie = new Cookie(name.toLowerCase() + "Contents", stringJoiner.toString());
            cookie.setPath("/");
            response.addCookie(cookie);
            model.addAttribute("is" +name + "Empty", false);
        }
    }

    private void handleRequest(String contents, String status,  Model model) {
        String flag = "is" + status + "Empty";
        String list = "book" + status;
        if (contents == null || contents.equals("")) {
            model.addAttribute(flag, true);
            model.addAttribute("sumPriceOld", 0);
            model.addAttribute("sumPrice", 0);
        } else {
            model.addAttribute(flag, false);
            contents = contents.startsWith("/") ? contents.substring(1) : contents;
            contents = contents.endsWith("/") ? contents.substring(0, contents.length() - 1) :
                    contents;
            String[] cookieSlugs = contents.split("/");
            List<Book> booksFromCookieSlugs = bookRepository.findBooksBySlugIn(cookieSlugs);
            model.addAttribute(list, booksFromCookieSlugs);
            int sumPriceOld = 0;
            int sumPrice = 0;
            for (Book book: booksFromCookieSlugs) {
                sumPriceOld += book.getPriceOld();
                sumPrice += book.getDiscountedPrice();
            }
            model.addAttribute("sumPriceOld", sumPriceOld);
            model.addAttribute("sumPrice", sumPrice);
        }

    }

}
