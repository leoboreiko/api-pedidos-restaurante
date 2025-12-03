package com.restaurant.services;

import com.restaurant.exceptions.EntityNotFoundException;
import com.restaurant.models.Order;
import com.restaurant.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {


    @Autowired
    private OrderRepository repository;

    public List<Order> findAll() {
        return repository.findAll();

    }

    public Order findById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));
    }

    public List<Order> findAllByName(String name) {
        List<Order> orders = repository.findByNameContainingIgnoreCase(name);
        if (orders.isEmpty()) {
            throw new EntityNotFoundException("Nenhum pedido encontrado com o nome: " + name);
        }
        return orders;
    }


    public Order save(Order order) {
        if (order.getItems() != null) {
            order.getItems().forEach(item -> item.setOrder(order));
        }
        return repository.save(order);
    }



    public Order update(Long id, Order data) {
        Order order = findById(id);
        order.setName(data.getName());
        order.setFinalValue(data.getFinalValue());
        order.setStatus(data.getStatus());
        order.setItems(data.getItems());
        return repository.save(order);
    }
}