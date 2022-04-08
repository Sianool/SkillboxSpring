package com.example.MyBookShopApp.data.services;

import com.example.MyBookShopApp.data.repository.BookRepository;
import com.example.MyBookShopApp.data.repository.TagRepository;
import com.example.MyBookShopApp.struct.book.Book;
import com.example.MyBookShopApp.struct.genre.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagService {

    private final TagRepository tagRepository;
    private final BookRepository bookRepository;

    public TagService(TagRepository tagRepository, BookRepository bookRepository) {
        this.tagRepository = tagRepository;
        this.bookRepository = bookRepository;
    }

    public List<TagEntity> getTagData() {
        return tagRepository.findAll();
    }

    public TagEntity getTagBySlug(String slug) {
        return tagRepository.findBySlug(slug);
    }

    public Page<Book> getPageOfTagBook(String slug, Integer offset, Integer limit) {
        Pageable nextPage = PageRequest.of(offset, limit, Sort.Direction.DESC, "pubDate", "title");
        return bookRepository.findAllBooksByTagsSlug(slug, nextPage);
    }
}
