package com.controller;

import com.model.Review;
import com.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("review")
public class ReviewController {
    ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @GetMapping("get")
    public List<Review> getReviews(){
        return reviewService.findAllReviews();
    }

    @PostMapping("post")
    public Review postReview(@RequestBody Review review){
        return reviewService.saveReview(review);
    }
}
