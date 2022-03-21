package com.repository;

import com.model.Movie;
import com.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface MovieRepository extends JpaRepository<Movie,Integer> {

    @Query("from Movie where id = :id")
    Movie findById(int id);
}
