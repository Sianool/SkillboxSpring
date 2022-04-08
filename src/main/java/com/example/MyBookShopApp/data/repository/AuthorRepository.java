package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.struct.Author;
import com.example.MyBookShopApp.struct.book.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Author findAuthorBySlug(String slug);

}
