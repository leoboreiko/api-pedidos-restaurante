package com.restaurant.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class RecipeRequestDTO {
    
    @NotBlank(message = "O nome é obrigatório")
    private String name;
    
    private String description;
    
    @NotNull(message = "O preço é obrigatório")
    @PositiveOrZero(message = "O preço deve ser positivo ou zero")
    private BigDecimal price; 
    
    private String quantityWeight;
    
    private Boolean available = true;
}