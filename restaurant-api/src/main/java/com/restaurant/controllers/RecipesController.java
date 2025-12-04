package com.restaurant.controllers;

import com.restaurant.dtos.RecipeRequestDTO;
import com.restaurant.dtos.RecipeResponseDTO;
import com.restaurant.services.RecipesService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/recipes")
public class RecipesController {

    private final RecipesService recipesService;

    // MELHOR PRÁTICA: Injeção por Construtor
    public RecipesController(RecipesService recipesService) {
        this.recipesService = recipesService;
    }

    // Criar receita: Retorna 201 CREATED
    @PostMapping
    public ResponseEntity<RecipeResponseDTO> create(@Valid @RequestBody RecipeRequestDTO requestDTO) {
        RecipeResponseDTO responseDTO = recipesService.create(requestDTO); 

        // Cria o URI (ex: /recipes/5) para o status 201 Created
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(responseDTO.getId())
                .toUri();
        
        return ResponseEntity.created(location).body(responseDTO);
    }

    // Listar todas as receitas: Retorna 200 OK
    @GetMapping
    public ResponseEntity<List<RecipeResponseDTO>> list() {
        return ResponseEntity.ok(recipesService.listAll());
    }
    
    // Buscar receita por ID: Retorna 200 OK (ou 404 via ControllerAdvice)
    @GetMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> search(@PathVariable Long id) {
        return ResponseEntity.ok(recipesService.searchById(id));
    }
    
    // Atualizar a receita por ID: Retorna 200 OK
    @PutMapping("/{id}")
    public ResponseEntity<RecipeResponseDTO> update(@PathVariable Long id, @Valid @RequestBody RecipeRequestDTO requestDTO) {
        return ResponseEntity.ok(recipesService.update(id, requestDTO));
    }
    
    // Deletar a receita: Retorna 204 NO CONTENT (melhor prática para DELETE)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        recipesService.delete(id);
        return ResponseEntity.noContent().build();
    }
}