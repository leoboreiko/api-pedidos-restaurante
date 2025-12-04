package com.restaurant.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal; 
@Entity
@Table(name = "recipes") 
@Getter 
@Setter
@NoArgsConstructor 
public class Recipes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String description;
    
    private BigDecimal price; 
    
    private String quantityWeight; 
    private Boolean available;
}