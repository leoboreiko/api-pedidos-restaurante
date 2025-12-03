package com.restaurant.repositories;

import com.restaurant.models.Order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByNameContainingIgnoreCase(String name);

}
