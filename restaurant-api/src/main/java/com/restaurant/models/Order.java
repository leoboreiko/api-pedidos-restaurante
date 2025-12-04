package com.restaurant.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter @NoArgsConstructor
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String customerName; 
    @Column(name = "final_value")
    private BigDecimal finalValue = BigDecimal.ZERO; 

    @Enumerated(EnumType.STRING)
    private OrderStatus status = OrderStatus.PENDING;

    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY) 
    @JsonManagedReference
    private List<OrderItem> items = new ArrayList<>();
}