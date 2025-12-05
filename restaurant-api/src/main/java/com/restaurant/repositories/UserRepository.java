package com.restaurant.repositories;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import com.restaurant.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    
}
