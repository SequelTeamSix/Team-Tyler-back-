package com.service;

import com.repository.AuthorRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    private AutoCloseable autoCloseable;
    private AuthorService underTest;

    @BeforeEach
    void setUp(){
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new AuthorService(authorRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void findById() {
    }

    @Test
    void saveAuthor() {
    }

    @Test
    void getAllUserReviews() {
        //when
        //underTest.getAllUserReviews();
        //then
        //verify(authorRepository).findAll();
    }

    @Test
    void getAllAuthors() {
        //when
        underTest.getAllAuthors();
        //then
        verify(authorRepository).findAll();
    }

    @Test
    void findByUserName() {
    }
}