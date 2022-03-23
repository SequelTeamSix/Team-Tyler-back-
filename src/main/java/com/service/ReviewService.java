package com.service;

import com.model.Review;
import com.repository.ReviewRepository;
import org.springframework.stereotype.Component;

@Component
public class ReviewService {
    ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository){
        this.reviewRepository = reviewRepository;
    }
    public Review saveReview(Review review){
        return reviewRepository.save(review);
    }
    public void deleteReview(Review review){
        reviewRepository.delete(review);
    }
    public Review findById(int id){
        return reviewRepository.findById(id);
    }
}