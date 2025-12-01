package com.restaurant.services;

import com.restaurant.exceptions.InvalidDatabaseActionException;
import com.restaurant.models.Recipes;
import com.restaurant.repositories.RecipesRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecipesService {

    private final RecipesRepository repository;

    public RecipesService(RecipesRepository repository) {
        this.repository = repository;
    }

    // Criar receita
    public Recipes create(Recipes recipes) {
        return repository.save(recipes);
    }

    // Listar todas as receitas
    public List<Recipes> listAll() {
        return repository.findAll();
    }

    // Buscar receita por ID
    public Recipes searchById(Long id){
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Receita não encontrada: " + id));
    }

    // Atualizar a receita por ID
    public Recipes update(Long id, Recipes newData){
        try{
            Recipes recipe = searchById(id);
        
            recipe.setName(newData.getName());
            recipe.setDescription(newData.getDescription());
            recipe.setPrice(newData.getPrice());
            recipe.setQuantityWeight(newData.getQuantityWeight());
            recipe.setAvailable(newData.getAvailable());
            
            return repository.save(recipe);
        } catch (IllegalArgumentException e) {
            throw new EntityNotFoundException("Receita não encontrada: " + id);
        } catch (OptimisticLockingFailureException e){
            throw new InvalidDatabaseActionException("Receita não encontrada: " + id);
        }
        
    }

    // Deletar receita
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
