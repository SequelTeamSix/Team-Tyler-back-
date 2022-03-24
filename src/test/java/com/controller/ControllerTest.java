package com.controller;


import com.model.Author;
import com.model.Movie;
import com.model.Review;
import com.service.AuthorService;
import com.service.MovieService;
import com.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@WebMvcTest(Controller.class)
public class ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthorService authorService;
    @MockBean
    private ReviewService reviewService;
    @MockBean
    private MovieService movieService;

    private List<Author> authors;
    private Author rogerEbert;
    private List<Review> reviews;
    private Review review;

    private static boolean deleteCalled = false;
    private Author kevin = new Author();
    private Review jaws = new Review();

    @BeforeEach
    public void setup(){
        authors = new ArrayList<>();
        Author rogerEbert = new Author();
        rogerEbert.setUserName("rebert");
        rogerEbert.setPassWord("password");
        authors.add(rogerEbert);


        kevin.setUserName("kevkev");
        kevin.setPassWord("password");
        authors.add(kevin);

        reviews = new ArrayList<>();

        jaws.setAuthor(rogerEbert);
        jaws.setRating(9.0);
        jaws.setComment("It was mad scary bruh");
        reviews.add(jaws);
    }


    @Test
    void getReviews() throws Exception {
        Movie movie = new Movie();
        when(movieService.findAllReviews(movie.getId())).thenReturn(reviews);
        this.mockMvc.perform(get("/reviews"));
    }

    @Test
    void register() throws Exception {
        when(authorService.saveAuthor(any(Author.class))).thenReturn(kevin);
        this.mockMvc.perform(put("/signUp")
                        .content("{\"userName\":\"kevkev\",\"passWord\":\"password\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void postReview() throws Exception {
        when(reviewService.saveReview(any(Review.class))).thenReturn(review);
        this.mockMvc.perform(put("/postReview")
                        .content("{\"author\":{\"userName\":\"rogerEbert\",\"passWord\":\"password\"}\",\"rating\":9.0, " +
                                "\"comment\":\"It was mad scary bruh\"}")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print());
    }

    @Test
    void getAllUserReviews() {
    }

    @Test
    void removeReview() {
    }

    @Test
    void login() {
    }
}
