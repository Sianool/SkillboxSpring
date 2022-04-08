package com.example.MyBookShopApp.data.repository;

import com.example.MyBookShopApp.struct.genre.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface TagRepository extends JpaRepository<TagEntity, Integer> {

    TagEntity findBySlug(String slug);

}
