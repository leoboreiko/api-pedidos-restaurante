package com.restaurant.dtos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecipeResponseDTO {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String quantityWeight;
    private Boolean available;
}