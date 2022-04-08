package com.example.MyBookShopApp.controllers;

import com.example.MyBookShopApp.data.dto.SearchWordDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class SearchController {

    @ModelAttribute("searchWordDto")
    public SearchWordDto searchWordDro() {
        return new SearchWordDto();
    }
}
