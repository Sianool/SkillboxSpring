package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.struct.book.Book;
import com.example.MyBookShopApp.struct.genre.GenreEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public interface BookRepository extends JpaRepository<Book,Integer> {

    List<Book> findBooksByAuthorId(Integer name);

    Page<Book> findBooksByAuthorId(Integer id, Pageable pageable);

    Page<Book> findBooksById(Integer id, Pageable pageable);

    List<Book> findBooksBySlugIn(String[] slugs);

    Book findBookBySlug(String slug);

    @Query("FROM Book ORDER BY pubDate DESC, title DESC")
    List<Book> findAllOrderByPubDate();

    List<Book> findBooksByTitleContaining(String title);

    List<Book> findBooksByPriceOldBetween(Integer min, Integer max);

    Page<Book> findByPubDateBetween(LocalDate from, LocalDate to, Pageable nextPage);

    List<Book> findBooksByPriceOldIs(Integer price);

    @Query("from Book where isBestseller=1")
    List<Book> getBestSellers();

    @Query(value = "SELECT * FROM books WHERE discount=(SELECT MAX(discount) from books)", nativeQuery = true)
    List<Book> getBooksWithMaxDiscount();

    Page<Book> findBookByTitleContaining(String bookTitle, Pageable nextPage);

    List<Book> findBooksByGenresId(Integer id);

    Page<Book> findBooksByGenresSlug(String slug, Pageable nextPage);

    List<Book> findAllBooksByTagsId(Integer id);

    Page<Book> findAllBooksByTagsSlug(String slug, Pageable nextPage);

    Page<Book> findAllByOrderByBookPopularityDesc(Pageable nexPage);

    Page<Book> findAllByOrderByBookRatingDesc(Pageable nexPage);

}
