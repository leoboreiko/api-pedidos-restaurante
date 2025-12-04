package com.restaurant.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal; 

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "order_item")
public class OrderItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String notes; 
    
    @Column(name = "unit_price")
    private BigDecimal unitPrice; 
    
    private Integer quantity;
    
    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "recipe_id") 
    private Recipes recipe;

    
    @ManyToOne(fetch = FetchType.LAZY) 
    @JoinColumn(name = "order_id")
    @JsonBackReference 
    private Order order;
}