package com.restaurant.controllers;

import com.restaurant.dtos.OrderRequestDTO;
import com.restaurant.dtos.OrderResponseDTO;
import com.restaurant.models.OrderStatus;
import com.restaurant.services.OrderService;

import jakarta.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService service;

    // Injeção por Construtor
    public OrderController(OrderService service) {
        this.service = service;
    }

    // Listar todos: GET /orders
    @GetMapping
    public ResponseEntity<List<OrderResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    // Buscar por ID: GET /orders/{id}
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    // Busca por nome (usando Query Param e GET): GET /orders/search?name=joao
    @GetMapping("/search") 
    public ResponseEntity<List<OrderResponseDTO>> findByCustomerName(@RequestParam String name) {
        return ResponseEntity.ok(service.findAllByName(name));
    }

    // Criar pedido: POST /orders
    @PostMapping
    public ResponseEntity<OrderResponseDTO> create(@Valid @RequestBody OrderRequestDTO requestDTO) {
        OrderResponseDTO saved = service.create(requestDTO);
        
        // Retorna 201 CREATED e o URI do recurso
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        
        return ResponseEntity.created(location).body(saved);
    }
    
    // Atualizar detalhes do pedido (PUT /orders/{id})
    @PutMapping("/{id}")
    public ResponseEntity<OrderResponseDTO> update(@PathVariable Long id, @Valid @RequestBody OrderRequestDTO requestDTO) {
        // Observação: Este PUT só deve atualizar campos simples como o nome do cliente.
        return ResponseEntity.ok(service.update(id, requestDTO));
    }

    // Atualizar STATUS (Melhor Prática: endpoint específico para atualização de status)
    // PATCH /orders/{id}/status?newStatus=FINISHED
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderResponseDTO> updateStatus(@PathVariable Long id, @RequestParam OrderStatus newStatus) {
        OrderResponseDTO updatedOrder = service.updateStatus(id, newStatus);
        return ResponseEntity.ok(updatedOrder);
    }
}