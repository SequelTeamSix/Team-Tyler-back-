package com.response;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReviewResponse {
    private int id;
    private double rating;
    private String comment;
    private String userName;
    private int movieId;
}
