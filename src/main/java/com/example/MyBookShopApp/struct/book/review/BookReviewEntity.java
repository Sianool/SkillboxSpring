package com.example.MyBookShopApp.struct.book.review;

import com.example.MyBookShopApp.struct.book.Book;
import com.example.MyBookShopApp.struct.user.UserEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "book_review")
public class BookReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne
    @JoinColumn(name = "rating_id",referencedColumnName = "id")
    private BookRatingEntity bookRatingEntity;

    @Column(columnDefinition = "TIMESTAMP NOT NULL")
    private LocalDateTime time;

    @Column(columnDefinition = "TEXT NOT NULL")
    private String text;

    @OneToMany(mappedBy = "bookReviewEntity")
    @JsonIgnore
    private List<BookReviewLikeEntity> bookReviewLikeEntities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public BookRatingEntity getBookRatingEntity() {
        return bookRatingEntity;
    }

    public void setBookRatingEntity(BookRatingEntity bookRatingEntity) {
        this.bookRatingEntity = bookRatingEntity;
    }

    public String getStringTime() {
        return getTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public List<BookReviewLikeEntity> getBookReviewLikeEntities() {
        return bookReviewLikeEntities;
    }

    public void setBookReviewLikeEntities(List<BookReviewLikeEntity> bookReviewLikeEntities) {
        this.bookReviewLikeEntities = bookReviewLikeEntities;
    }

    public Integer getLikesCount() {
        int result = 0;
        for (BookReviewLikeEntity like : getBookReviewLikeEntities()) {
            if (like.getValue() == 1) result++;
        }
        return result;
    }

    public Integer getDislikesCount() {
        int result = 0;
        for (BookReviewLikeEntity like : getBookReviewLikeEntities()) {
            if (like.getValue() == -1) result++;
        }
        return result;
    }
}
