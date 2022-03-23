package com.service;

import com.model.Author;
import com.model.Movie;
import com.model.Review;
import com.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    private AutoCloseable autoCloseable;
    private AuthorService underTest;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AuthorService(authorRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findById() {
    }

    @Test
    void saveAuthor() {
    }

    @Test
    void getAllUserReviews() {
        Author author = new Author();
        author.setPassWord("password");
        author.setUserName("user");
        authorRepository.save(author);

        Movie movie = new Movie();

        Review review = new Review();
        review.setAuthor(author);
        review.setComment("Wuz good");
        review.setRating(5.5);
        review.setMovie(movie);

        System.out.println(review.toString());
        //when
        underTest.getAllUserReviews("user");
        //then
        //verify(authorRepository).findAll();
    }

    @Test
    void getAllAuthors() {
        //when
        underTest.getAllAuthors();
        //then
        verify(authorRepository).findAll();
    }

    @Test
    void findByUserName() {
    }
}