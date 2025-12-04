package com.restaurant.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderItemRequestDTO {
    
    @NotNull(message = "O ID da receita é obrigatório para o item")
    private Long recipeId; 
    
    @Positive(message = "A quantidade deve ser positiva")
    private Integer quantity;
    
    private String notes;
}