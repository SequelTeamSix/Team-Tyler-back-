package com.service;

import com.model.Author;
import com.model.Review;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import com.repository.AuthorRepository;

import java.util.List;

@Component
public class AuthorService {
    AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository){
        this.authorRepository = authorRepository;
    }

    public Author findById(int id){
        return authorRepository.findById(id);
    }
    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }


    public List<Review> getAllUserReviews(String userName){
        return authorRepository.findAllUserReviews(userName).getReviews();
    }

    public Author findByUserName(String userName) {
        return authorRepository.findByUserName(userName);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }
}