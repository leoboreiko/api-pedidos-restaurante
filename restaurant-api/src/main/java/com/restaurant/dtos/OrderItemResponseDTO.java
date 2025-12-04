package com.restaurant.dtos;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDTO {
    private Long id;
    private String recipeName; // Nome da receita, para fácil leitura
    private Integer quantity;
    private BigDecimal unitPrice; // Preço no momento da compra (pode ser diferente do atual)
    private String notes;
}