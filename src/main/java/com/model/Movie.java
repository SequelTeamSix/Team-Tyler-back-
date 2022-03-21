package com.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@JsonIdentityInfo(
        //this is to stop recursive hibernate joins
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class Movie {
    @Id
    private int id;

    @OneToMany(mappedBy = "movie")
    private List<Review> reviews;
<<<<<<< HEAD
}
=======
}
>>>>>>> 364d20e1308109e69c400f2dd3f1fec4786a8283
