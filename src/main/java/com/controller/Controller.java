package com.controller;


import com.model.*;
import com.service.AuthorService;
import com.service.MovieService;
import com.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import com.model.Encryption;

import java.util.*;

@RestController
@AllArgsConstructor
@NoArgsConstructor
public class  Controller {
    @Autowired
    MovieService movieService;
    @Autowired
    ReviewService reviewService;
    @Autowired
    AuthorService authorService;

    
    @GetMapping("/test")
    public String message(){
        return "Congrats, azure is connected and the app is running";
    }
    
    // getting reviews by movie id
    @GetMapping("/reviews")
    public List<Review> getReviews(@RequestParam("id") int id){
        return movieService.findAllReviews(id);
    }
    @PostMapping("/signUp")
    public Author register(@RequestParam("firstName") String fistName,@RequestParam("lastName")
            String lastName, @RequestParam("userName") String userName,
                           @RequestParam("passWord") String passWord){
        Author author = new Author();
        Name name = new Name();
        name.setFirstName(fistName);
        name.setLastName(lastName);
        author.setName(name);
        Encryption encryption = new Encryption(passWord);
        author.setUserName(userName);
        author.setPassWord(String.valueOf(encryption.getEncryptedPassWord()));

        authorService.saveAuthor(author);
        return author;
    }

    @PutMapping("/postReview")
    public List<Review> postReview(@RequestParam("authorId") int authorId, @RequestParam("comment") String comment, @RequestParam("rating") double rating,
                                   @RequestParam("movieId") int movieId){
        Author author = authorService.findById(authorId);
        Movie movie = new Movie();
        List<Review> authorReviews = new ArrayList<>();
        boolean valid = false;
        for(int i=0;i<author.getReviews().size();i++){
            if (author.getReviews().get(i).getMovie().getId() == movieId){
                System.out.println("already has a review");
                valid = true;
                i=author.getReviews().size()-1;
                author.getReviews().get(i).setComment(comment);
                author.getReviews().get(i).setRating(rating);
                authorService.saveAuthor(author);
            }
        }
        if (!valid){
            movie.setId(movieId);

            Review review = new Review();
            review.setMovie(movie);
            review.setComment(comment);
            review.setRating(rating);
            review.setAuthor(author);

            authorReviews.add(review);
            author.setReviews(authorReviews);

//            movieReviews.add(review);
            movieService.saveMovie(movie);
            reviewService.saveReview(review);
        }
        return authorReviews;
    }

    @GetMapping("/userReviews")
    public List<Review> getAllUserReviews(@RequestParam("userName") String userName){
        return authorService.getAllUserReviews(userName);
    }
    //removing a review. I couldn't do just delete review because I couldn't bypass spring first level cache
    @PostMapping("/removeReview")
    public  List<Review> removeReview(@RequestParam("reviewId") int reviewId,@RequestParam("authorId") int authorId){
        Author author = authorService.findById(authorId);
        Author author1 = new Author();
        author1.setId(author.getId());
        author1.setName(author.getName());
        author1.setUserName(author.getUserName());
        author1.setPassWord(author.getPassWord());
        List<Review> reviews= new ArrayList<>();
        author1.setReviews(reviews);
        for (Review r: author.getReviews()){
            if (r.getId() == reviewId){
                reviewService.deleteReview(reviewId);
                System.out.println(author1.getReviews());
                return author1.getReviews();
            }else {
                reviews.add(r);
            }
        }
        authorService.saveAuthor(author1);

        return author1.getReviews();
    }

    @PostMapping("/login")
    public Boolean login(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord) {
        Author author = authorService.findByUserName(userName);
        System.out.println(passWord);
        System.out.println(author.getPassWord());
        Encryption cryptPassword = new Encryption(passWord);
        System.out.println(cryptPassword.getEncryptedPassWord());
        if (cryptPassword.getEncryptedPassWord().equals(author.getPassWord())){
            System.out.println("Welcome!");
            return true;
        }else{
            System.out.println(passWord.equals(author.getPassWord()));
            System.out.println(author.getId());
            return false;
        }
    }
}





