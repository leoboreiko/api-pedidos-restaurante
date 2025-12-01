package com.restaurant.controllers;

import com.restaurant.models.Order;
import com.restaurant.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService service;


    @GetMapping
    public List<Order> findAll() {
        return service.findAll();
    }


    @GetMapping("/{id}")
    public Order findById(@PathVariable Long id) {
        return service.findById(id);
    }


    @PostMapping
    public Order save(@RequestBody Order order) {
        return service.save(order);
    }


    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        return service.update(id, order);
    }
}
