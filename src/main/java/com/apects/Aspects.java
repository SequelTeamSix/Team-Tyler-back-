package com.apects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;


@Aspect
@Component
public class Aspects {
    public static Logger logger = Logger.getLogger(Aspects.class);

    @AfterReturning("execution(* getReviews(..))")
    public void gettingReviews(JoinPoint joinPoint){
        logger.info("Getting all reviews for movie with Id = "+ Arrays.toString(joinPoint.getArgs()));
    }

    @AfterThrowing("execution(* getReviews(..))")
    public void reviewDExist(JoinPoint joinPoint){
        logger.warn("Trying to get a review that doesn't exist "+joinPoint.getSignature());
    }
    @Before("execution(* register(..))")
    public void beforeCreatingAccount(){
        logger.info("Using trying to create an account");
    }

    @AfterReturning("execution(* register(..))")
    public void signUpSuccessfully(){
        logger.info("User has successfully created an account");
    }

    @AfterThrowing("execution(* register(..))")
    public void signUpUnSuccessful(){
        logger.warn("Account creation was unsuccessful due to wrong param or user already exist in database");
    }

    @Before("execution(* login(..))")
    public void beforeLogin(){

        logger.info("User trying to login");
    }

    @Before("execution(* remove*(..))")
    public void removeReview(){
        logger.info("User trying to remove a review");
    }

    @AfterReturning("execution(* getAllUserReviews(..))")
    public void allUserReviews(){
        logger.info("Getting all user reviews");
    }


}
