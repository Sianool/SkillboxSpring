package com.example.MyBookShopApp.struct.book.file;


import com.example.MyBookShopApp.struct.book.Book;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
public class BookFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String hash;

    @ManyToOne
    @JoinColumn(name = "type_id", referencedColumnName = "id")
    private BookFileTypeEntity bookFileTypeEntity;

    private String path;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public String getBookFileExtensionByTypeId() {
        return bookFileTypeEntity.getName();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public BookFileTypeEntity getBookFileTypeEntity() {
        return bookFileTypeEntity;
    }

    public void setBookFileTypeEntity(BookFileTypeEntity bookFileTypeEntity) {
        this.bookFileTypeEntity = bookFileTypeEntity;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
