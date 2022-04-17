package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.SearchWordDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SearchController {

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDro() {
        return new SearchWordDto();
    }

    @ModelAttribute("cartBooksCount")
    public Integer cartBooksCount(@CookieValue(value = "cartContents", required = false) String cartContents) {
        return getInteger(cartContents);
    }

    @ModelAttribute("postponedBooksCount")
    public Integer postponedContentsBooksCount(@CookieValue(value = "postponedContents", required = false) String postponedContents) {
        return getInteger(postponedContents);
    }

    private Integer getInteger(@CookieValue(value = "postponedContents", required = false) String postponedContents) {
        if (postponedContents == null || postponedContents.equals("")) {
            return 0;
        } else  {
            postponedContents = postponedContents.startsWith("/") ? postponedContents.substring(1) : postponedContents;
            postponedContents = postponedContents.endsWith("/") ? postponedContents.substring(0, postponedContents.length() - 1) :
                    postponedContents;
            String[] cookieSlugs = postponedContents.split("/");
            return cookieSlugs.length;
        }
    }
}
