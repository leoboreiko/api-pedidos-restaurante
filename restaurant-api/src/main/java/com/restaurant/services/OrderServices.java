package com.restaurant.services;

import com.restaurant.models.Order;
import com.restaurant.repositories.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServices {
    private final OrderRepository repository;

    public OrderServices(OrderRepository repository){
        this.repository = repository;
    }
    // Listar todos os pedidos
    public List<Order> findAll(){
        return repository.findAll();
    }
    //Listar os pedidos por ID
    public Order findById(Long id){
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }


}
