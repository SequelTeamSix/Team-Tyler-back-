package com.service;

import com.model.Author;
import com.model.Movie;
import com.model.Review;
import com.repository.AuthorRepository;
import com.repository.ReviewRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


class AuthorServiceTest {
//    Author tman17 = new Author();
//    Author tman17Mock= new Author();
//    Connection mockConnection;
//    PreparedStatement mockPrepared;
//    ResultSet mockResultForGetPainting;

//    @BeforeAll
//    public void SetUpMocks() throws SQLException{
//        tman17.setUserName("tman17");
//        tman17.getPassWord("password");
//    }

    private AuthorService authorService;
    private AuthorRepository authorRepository;
    private ReviewService reviewService;
    private ReviewRepository reviewRepository;

    private List<Author> authors;
    private Author author;
    private List<Review> reviews;
    private Review review;

    private static boolean deleteCalled = false;

    @BeforeEach
    void setup(){
        authorRepository = Mockito.mock(AuthorRepository.class);
        authorService = new AuthorService(authorRepository);

        reviewRepository = Mockito.mock(ReviewRepository.class);
        reviewService = new ReviewService(reviewRepository);

        authors = new ArrayList<>();
        Author rogerEbert = new Author();
        rogerEbert.setUserName("rebert");
        rogerEbert.setPassWord("password");
        authors.add(rogerEbert);


        reviews = new ArrayList<>();
        Review jaws = new Review();
        jaws.setAuthor(rogerEbert);
        jaws.setRating(9.0);
        jaws.setComment("It was mad scary bruh");
        reviews.add(jaws);
    }

    @Test
    void findById() {
    }

    @Test
    void saveAuthor() {

    }

    @Test
    void getAllUserReviews() {
       when(authorRepository.findAllUserReviews("rebert")).thenReturn(author);
       assertEquals(authorService.getAllUserReviews("rebert"), reviews);
    }

    @Test
    void getAllAuthors() {
        when(authorRepository.findAll()).thenReturn(authors);
        assertEquals(authorService.getAllAuthors(), authors);
    }

    @Test
    void findByUserName() {
    }
}