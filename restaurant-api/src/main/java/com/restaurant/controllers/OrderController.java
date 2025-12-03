package com.restaurant.controllers;

import com.restaurant.dtos.OrderDTO;
import com.restaurant.models.Order;
import com.restaurant.services.OrderService;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/filter-by-name")
    public List<Order> findAllByName(@RequestParam String name) {
        return service.findAllByName(name);
    }

    @PutMapping("/{id}")
    public Order update(@PathVariable Long id, @RequestBody Order order) {
        return service.update(id, order);
    }

    @PostMapping
    public ResponseEntity<Order> create(@Valid @RequestBody OrderDTO dto) {
        Order order = new Order();
        order.setName(dto.getName());
        order.setFinalValue(dto.getFinalValue());
        Order saved = service.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }



}
