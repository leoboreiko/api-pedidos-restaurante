package com.restaurant.dtos;

import com.restaurant.models.OrderStatus;
import java.math.BigDecimal;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDTO {
    private Long id;
    private String customerName;
    private BigDecimal finalValue;
    private OrderStatus status;
    private List<OrderItemResponseDTO> items; 
}