package com.restaurant.services;

import com.restaurant.dtos.RecipeRequestDTO;
import com.restaurant.dtos.RecipeResponseDTO;
import com.restaurant.exceptions.EntityNotFoundException;
import com.restaurant.models.Recipes;
import com.restaurant.repositories.RecipesRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecipesService {
    
    private final RecipesRepository repository;

    // MELHOR PRÁTICA: Injeção por Construtor
    public RecipesService(RecipesRepository repository) {
        this.repository = repository;
    }
    
    // --- Mapeamento DTO <-> Entity (Pode ser feito por uma classe Mapper externa) ---
    private Recipes toEntity(RecipeRequestDTO dto) { 
        Recipes entity = new Recipes();
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setQuantityWeight(dto.getQuantityWeight());
        entity.setAvailable(dto.getAvailable());
        return entity;
    }
    
    private RecipeResponseDTO toResponseDTO(Recipes entity) { 
        return new RecipeResponseDTO(
            entity.getId(), 
            entity.getName(), 
            entity.getDescription(), 
            entity.getPrice(), 
            entity.getQuantityWeight(), 
            entity.getAvailable()
        );
    }
    // ---------------------------------------------------------------------------------

    @Transactional
    public RecipeResponseDTO create(RecipeRequestDTO requestDTO) {
        Recipes newRecipe = toEntity(requestDTO);
        Recipes savedRecipe = repository.save(newRecipe);
        return toResponseDTO(savedRecipe);
    }
    
    @Transactional(readOnly = true)
    public List<RecipeResponseDTO> listAll() {
        return repository.findAll().stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public RecipeResponseDTO searchById(Long id) {
        Recipes recipe = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada com ID: " + id));
        return toResponseDTO(recipe);
    }
    
    @Transactional
    public RecipeResponseDTO update(Long id, RecipeRequestDTO requestDTO) {
        Recipes recipeToUpdate = repository.findById(id)
             .orElseThrow(() -> new EntityNotFoundException("Receita não encontrada para atualização com ID: " + id));

        // Aplica atualizações do DTO
        recipeToUpdate.setName(requestDTO.getName());
        recipeToUpdate.setDescription(requestDTO.getDescription());
        recipeToUpdate.setPrice(requestDTO.getPrice());
        recipeToUpdate.setQuantityWeight(requestDTO.getQuantityWeight());
        recipeToUpdate.setAvailable(requestDTO.getAvailable());

        return toResponseDTO(repository.save(recipeToUpdate));
    }

    @Transactional
    public void delete(Long id) {
        // Verifica se existe (lança 404 se não existir)
        repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Não é possível deletar. Receita não encontrada com ID: " + id));
        repository.deleteById(id);
    }
}