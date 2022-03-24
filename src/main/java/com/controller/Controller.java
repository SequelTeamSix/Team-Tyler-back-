package com.controller;


import com.apects.Aspects;
import com.model.*;
import com.response.AuthorResponse;
import com.response.ReviewResponse;
import com.service.AuthorService;
import com.service.MovieService;
import com.service.ReviewService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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


    @PutMapping("/signUp")
    public AuthorResponse register(@RequestBody Author author){
        Encryption encryption = new Encryption(author.getPassWord());
        author.setPassWord(encryption.getEncryptedPassWord());
        author = authorService.saveAuthor(author);
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(author.getName());
        for(Review r: author.getReviews()){
            response.getReviews().add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),r.getAuthor().getId()));
        }
        return response;
    }

    @PutMapping("/postReview")
    public List<ReviewResponse> postReview(@RequestBody Review review){
        Author author = authorService.findById(review.getAuthor().getId());
        Movie movie = new Movie();
        List<Review> authorReviews = new ArrayList<>();
        boolean valid = false;
        List<ReviewResponse> reviews=new ArrayList<>();
        for(int i=0;i<author.getReviews().size();i++){
            if (author.getReviews().get(i).getMovie().getId() == review.getMovie().getId()){
                valid = true;
                i=author.getReviews().size()-1;
                author.getReviews().get(i).setComment(review.getComment());
                author.getReviews().get(i).setRating(review.getRating());
                authorService.saveAuthor(author);
                reviews.add(new ReviewResponse(author.getReviews().get(i).getId(),author.getReviews().get(i).getRating(),
                        author.getReviews().get(i).getComment(),author.getReviews().get(i).getAuthor().getUserName(),
                        author.getReviews().get(i).getMovie().getId()));
            }else {
                reviews.add(new ReviewResponse(author.getReviews().get(i).getId(),author.getReviews().get(i).getRating(),
                        author.getReviews().get(i).getComment(),author.getReviews().get(i).getAuthor().getUserName(),
                        author.getReviews().get(i).getMovie().getId()));
            }
        }
        if (!valid){
            movie.setId(review.getMovie().getId()
            );

            Review r = new Review();
            r.setMovie(movie);
            r.setComment(review.getComment());
            r.setRating(review.getRating());
            r.setAuthor(author);

            authorReviews.add(r);
            author.setReviews(authorReviews);
            movieService.saveMovie(movie);
            r=reviewService.saveReview(r);
            reviews.add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),
                    r.getMovie().getId()));
        }
        return reviews;
    }

    @GetMapping("/userReviews")
    public List<ReviewResponse> getAllUserReviews(@RequestParam("userName") String username){

        List<ReviewResponse> reviews =new ArrayList<>();
        List<Review> reviewList = authorService.getAllUserReviews(username);
        for (Review r: reviewList){
            reviews.add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),r.getMovie().getId()));

        }
        return reviews;
    }


    //removing a review. I couldn't do just delete review because I couldn't bypass spring first level cache
    @DeleteMapping("/removeReview")
    public  List<ReviewResponse> removeReview(@RequestBody Review re){
        Author author = authorService.findById(re.getAuthor().getId());
        List<ReviewResponse> responses =new ArrayList<>();
        for (Review r: author.getReviews()){
            if (r.getId() == re.getId()){
                reviewService.deleteReview(r);
                Aspects.logger.info(author.getUserName()+" has successfully remove a review");
            }else {
                responses.add(new ReviewResponse(r.getId(),r.getRating(),r.getComment(),r.getAuthor().getUserName(),r.getMovie().getId()));
            }
        }
        return responses;
    }

    @PostMapping("/login")
    public AuthorResponse login(@RequestBody Author at) {
        Author author = authorService.findByUserName(at.getUserName());
        //System.out.println(at.getPassWord());
        //System.out.println(author.getPassWord());
        Encryption cryptPassword = new Encryption(at.getPassWord());
        //System.out.println(cryptPassword.getEncryptedPassWord());
        AuthorResponse response = new AuthorResponse();
        response.setId(author.getId());
        response.setName(at.getName());

        if (cryptPassword.getEncryptedPassWord().equals(author.getPassWord())){
            //System.out.println("Welcome!");
            Aspects.logger.info(author.getUserName()+" has successfully logIn");
            return response;
        }else{
//            System.out.println(at.getPassWord().equals(author.getPassWord()));
//            System.out.println(author.getId());
            Aspects.logger.warn(at.getUserName()+" could not login due to wrong inputs");
            return null;
        }
    }
}





