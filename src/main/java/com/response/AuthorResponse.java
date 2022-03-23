package com.response;

import com.model.Name;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class AuthorResponse {
    private int id;
    private Name name;
    private List<ReviewResponse> reviews = new ArrayList<>();
}
