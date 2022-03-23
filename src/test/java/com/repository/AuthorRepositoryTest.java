package com.repository;

import com.model.Author;
import com.model.Review;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired
    private AuthorRepository underTest;
    @AfterEach
    void teardown(){
        underTest.deleteAll();
    }

    @Test
    void findById() {

    }

    @Test
    void save() {
    }

    @Test
    void findAllUserReviews() {
        //given
        Author author = new Author();
        author.setPassWord("password");
        author.setUserName("user");
        underTest.save(author);
        //when
        List<Review> reviews = author.getReviews();

    }

    @Test
    void findByUserName() {
        //given
        Author author = new Author();
        author.setPassWord("password");
        author.setUserName("user");
        underTest.save(author);
        //when
        Author exists = underTest.findByUserName("user");
        //Then
        assertSame("password", exists.getPassWord());
    }
}