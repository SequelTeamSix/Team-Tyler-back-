package com.controller;


import com.model.*;
import com.response.AuthorResponse;
import com.response.ReviewResponse;
import com.service.AuthorService;
import com.service.MovieService;
import com.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;

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
    public List<ReviewResponse> getReviews(@RequestParam("id") int id){
        List<ReviewResponse> reviews=new ArrayList<>();
        List<Review> reviewList =movieService.findAllReviews(id);
        for (Review r: reviewList){
            reviews.add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),r.getMovie().getId()));
        }
        return reviews;
    }


    @PostMapping("/signUp")
    public AuthorResponse register(@RequestParam("firstName") String fistName,@RequestParam("lastName")
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
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        for(Review r: author.getReviews()){
            response.getReviews().add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),r.getAuthor().getId()));
        }
        return response;
    }

    @PutMapping("/postReview")
    public List<ReviewResponse> postReview(@RequestParam("authorId") int authorId, @RequestParam("comment") String comment, @RequestParam("rating") double rating,
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
        List<ReviewResponse> reviews=new ArrayList<>();
        for (Review r: authorReviews){
            reviews.add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),r.getMovie().getId()));
        }
        return reviews;
    }

    @GetMapping("/userReviews")
    public List<ReviewResponse> getAllUserReviews(@RequestParam("userName") String userName){

        List<ReviewResponse> reviews =new ArrayList<>();
        List<Review> reviewList = authorService.getAllUserReviews(userName);
        for (Review r: reviewList){
            reviews.add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),r.getMovie().getId()));
        }
        return reviews;
    }


    //removing a review. I couldn't do just delete review because I couldn't bypass spring first level cache
    @PostMapping("/removeReview")
    public  List<ReviewResponse> removeReview(@RequestParam("reviewId") int reviewId,@RequestParam("authorId") int authorId){
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

                List<ReviewResponse> responses =new ArrayList<>();
                for (Review re: author1.getReviews()){
                    responses.add(new ReviewResponse(re.getId(),re.getRating(),re.getComment(),re.getAuthor().getUserName(),re.getMovie().getId()));
                }
                return responses;
            }else {
                reviews.add(r);
            }
        }
        authorService.saveAuthor(author1);

        List<ReviewResponse> responses =new ArrayList<>();
        for (Review r: author1.getReviews()){
            responses.add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),r.getMovie().getId()));
        }
        return responses;
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





