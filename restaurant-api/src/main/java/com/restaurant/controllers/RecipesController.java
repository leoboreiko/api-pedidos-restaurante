package com.restaurant.controllers;

import com.restaurant.models.Recipes;
import com.restaurant.services.RecipesService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/recipes")   // URL base: localhost:8080/recipes
public class RecipesController {

    private final RecipesService recipesService;

    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }
    // Criar receita
    @PostMapping
    public Recipes create(@RequestBody Recipes recipes){
        return recipesService.create(recipes);
    }
    // Listar todas as receitas
    @GetMapping
    public List<Recipes> list(){
        return recipesService.listAll();
    }
    // Buscar receita por ID
    @GetMapping("/{id}")
    public Recipes search(@PathVariable Long id){
        return recipesService.searchById(id);
    }
    // Atualizar a receita por ID
    @PutMapping("/{id}")
    public Recipes update(@PathVariable Long id, @RequestBody Recipes newData) {
        if (newData == null || newData.getId() == null) {
            throw new IllegalArgumentException("ID da receita é obrigatório");
        }
        return recipesService.update(id, newData);
    }
    // Deletar a receita
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id){
        recipesService.delete(id);
    }
}


