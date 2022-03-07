package com.example.MyBookShopApp.data;


import com.example.MyBookShopApp.struct.Author;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class AuthorsService {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorsService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Map<String, List<Author>> getAuthorMap() {
        List<Author> authors = authorRepository.findAll();

        return authors.stream().collect(Collectors.groupingBy((Author a) -> a.getLastName().substring(0,1)));
    }
}
