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
    public void deleteReview(int id){
        reviewRepository.deleteById(id);
    }
    public Review findById(int id){
        return reviewRepository.findById(id);
    }
<<<<<<< HEAD
}
=======
}
>>>>>>> 364d20e1308109e69c400f2dd3f1fec4786a8283
