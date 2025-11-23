package com.restaurant.repositories;

import com.restaurant.models.Recipes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipesRepository extends JpaRepository<Recipes, Long> {}
