package com.example.MyBookShopApp.struct.book.file;


import com.example.MyBookShopApp.struct.book.Book;

import javax.persistence.*;

@Entity
@Table(name = "book_file")
public class BookFileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "type_id")
    private Integer typeId;

    @ManyToOne
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

}
