package com.restaurant.models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data

public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String quantityWeight;
    private Boolean available;


    public Recipes() {

    }
}
