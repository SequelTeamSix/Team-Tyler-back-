package com.service;

import com.model.Author;
import com.model.Movie;
import com.model.Review;
import com.repository.AuthorRepository;
import com.repository.MovieRepository;
import org.springframework.stereotype.Component;


import java.util.ArrayList;
import java.util.List;

@Component
public class MovieService {
    MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    public List<Review> findAllReviews(int id){
        return movieRepository.findById(id).getReviews();
    }
    public Movie saveMovie(Movie movie){
        return movieRepository.save(movie);
    }
    public Movie findById(int id){
        return movieRepository.findById(id);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 364d20e1308109e69c400f2dd3f1fec4786a8283
