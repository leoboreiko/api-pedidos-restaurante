package com.restaurant.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequestDTO {
    
    @NotBlank(message = "O nome do cliente é obrigatório") 
    private String customerName; 

    @NotEmpty(message = "O pedido deve conter pelo menos um item")
    private List<OrderItemRequestDTO> items; 
}