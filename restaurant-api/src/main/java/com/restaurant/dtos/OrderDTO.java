package com.restaurant.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class OrderDTO {

    @NotBlank(message = "O nome é obrigatório")
    private String name;

    @Positive(message = "O valor final deve ser maior que 0")
    private double finalValue;

    
}
