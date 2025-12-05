package com.restaurant.services;

import com.restaurant.dtos.*;
import com.restaurant.exceptions.BadRequestException;
import com.restaurant.exceptions.ConflictException;
import com.restaurant.exceptions.EntityNotFoundException;
import com.restaurant.models.Order;
import com.restaurant.models.OrderItem;
import com.restaurant.models.OrderStatus;
import com.restaurant.models.Recipes;
import com.restaurant.repositories.OrderRepository;
import com.restaurant.repositories.RecipesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RecipesRepository recipesRepository;

    // Injeção por Construtor (Apenas Repositórios necessários)
    public OrderService(OrderRepository orderRepository, RecipesRepository recipesRepository) {
        this.orderRepository = orderRepository;
        this.recipesRepository = recipesRepository;
    }
    
    // --- Mapeamento Entity -> DTO de Resposta ---
    private OrderResponseDTO toResponseDTO(Order order) {
        List<OrderItemResponseDTO> itemDTOs = order.getItems().stream()
            .map(item -> new OrderItemResponseDTO(
                item.getId(), 
                item.getRecipe() != null ? item.getRecipe().getName() : "Item Removido", 
                item.getQuantity(), 
                item.getUnitPrice(), 
                item.getNotes()
            )).collect(Collectors.toList());

        return new OrderResponseDTO(
            order.getId(),
            order.getCustomerName(),
            order.getFinalValue(),
            order.getStatus(),
            itemDTOs
        );
    }

    // --- Lógica de Negócio Crucial: Criação e Cálculo ---
    
    @Transactional
    public OrderResponseDTO create(OrderRequestDTO requestDTO) {
        Order newOrder = new Order();
        newOrder.setCustomerName(requestDTO.getCustomerName());
        
        BigDecimal totalValue = BigDecimal.ZERO;
        
        // 1. Processa e valida cada item do DTO
        for (OrderItemRequestDTO itemDTO : requestDTO.getItems()) {
            
            Recipes recipe = recipesRepository.findById(itemDTO.getRecipeId())
                .orElseThrow(() -> new BadRequestException("Receita não encontrada com ID: " + itemDTO.getRecipeId()));
            
            if (!recipe.getAvailable()) {
                 throw new BadRequestException("Receita '" + recipe.getName() + "' não está disponível.");
            }
            
            // 2. Cria a entidade OrderItem
            OrderItem item = new OrderItem();
            item.setRecipe(recipe);
            item.setQuantity(itemDTO.getQuantity());
            item.setNotes(itemDTO.getNotes());
            
            // 3. Define o preço unitário (preço atual da receita no momento da compra)
            item.setUnitPrice(recipe.getPrice());
            
            // 4. Calcula o valor total do item
            BigDecimal itemTotal = recipe.getPrice().multiply(new BigDecimal(item.getQuantity()));
            totalValue = totalValue.add(itemTotal);
            
            // 5. CRUCIAL: Mantém a relação bi-direcional
            item.setOrder(newOrder); 
            newOrder.getItems().add(item);
        }
        
        // 6. Define o valor final calculado
        newOrder.setFinalValue(totalValue);
        
        // 7. Salva a Order. Os OrderItems são salvos automaticamente via Cascade.
        Order savedOrder = orderRepository.save(newOrder);
        return toResponseDTO(savedOrder);
    }
    
    // --- CRUD e Consultas ---

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAll() {
        return orderRepository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponseDTO findById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado com o ID: " + id));
        return toResponseDTO(order);
    }

    @Transactional(readOnly = true)
    public List<OrderResponseDTO> findAllByName(String name) {
        // Usa o Query Method definido no Repositório
        return orderRepository.findByCustomerNameContainingIgnoreCase(name).stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional
    public OrderResponseDTO update(Long id, OrderRequestDTO requestDTO) {
        Order orderToUpdate = orderRepository.findById(id)
             .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado para atualização com ID: " + id));

        // Regra de Negócio: Não permite alterar pedidos finalizados ou cancelados
        if (orderToUpdate.getStatus() == OrderStatus.FINISHED || orderToUpdate.getStatus() == OrderStatus.CANCELLED) {
             throw new ConflictException("Não é possível alterar um pedido com status: " + orderToUpdate.getStatus());
        }

        // Aplica atualizações simples do DTO
        orderToUpdate.setCustomerName(requestDTO.getCustomerName());
        
        // **Aviso:** A atualização de itens de pedido não está implementada aqui por ser complexa 
        // (requer lógica de delta: o que adicionar/remover/atualizar na lista existente).
        
        return toResponseDTO(orderRepository.save(orderToUpdate));
    }

    @Transactional
    public OrderResponseDTO updateStatus(Long id, OrderStatus newStatus) {
        Order order = orderRepository.findById(id)
             .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado para atualização de status com ID: " + id));
        
        if (order.getStatus() == OrderStatus.FINISHED && newStatus != OrderStatus.CANCELLED) {
             throw new ConflictException("Pedido finalizado só pode ter seu status alterado para CANCELLED.");
        }
        
        order.setStatus(newStatus);
        return toResponseDTO(orderRepository.save(order));
    }
}
            