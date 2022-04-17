package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.struct.book.file.BookFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookFileRepository extends JpaRepository<BookFileEntity, Integer> {

    public BookFileEntity findBookByHash(String hash);
}
