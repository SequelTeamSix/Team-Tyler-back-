package com.service;

import com.model.Author;
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
        Author author = authorRepository.findById(id);
        return author;
    }

    public Author findByUserName(String userName){
        Author author = authorRepository.findByUserName(userName);
        return author;
    }
    public Author saveAuthor(Author author){
        return authorRepository.save(author);
    }
    public List<Author> findAllAuthors(){
        return authorRepository.findAll();
    }
}