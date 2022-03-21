package com.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table
@Getter
@Setter
@JsonIdentityInfo(
        //this is to stop recursive hibernate joins
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Author {
    @Id
    @GeneratedValue
    private int id;
    @Column
    private Name name;
    @Column(unique = true)
    private String userName;
    @Column
    private int passWord;

    @OneToMany(mappedBy = "author",fetch = FetchType.EAGER)
    @ToString.Exclude
    private List<Review> reviews = new ArrayList<>();

}