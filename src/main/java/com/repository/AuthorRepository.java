package com.repository;

import com.model.Author;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface AuthorRepository extends JpaRepository<Author,Integer> {

    @Query("from Author where id = :id")
    Author findById(int id);

    Author save(Author author);

    @Query("from Author where userName = :userName")
    Author findAllUserReviews(String userName);

    @Query("from Author where userName = :userName")
    Author findByUserName(String userName);
}