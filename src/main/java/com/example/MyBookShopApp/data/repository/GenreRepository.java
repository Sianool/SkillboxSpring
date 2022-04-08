package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.struct.genre.GenreEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface GenreRepository extends JpaRepository<GenreEntity, Integer> {

    GenreEntity findBySlug(String slug);

    List<GenreEntity> findAllByOrderByParentIdAscIdAsc();
}
