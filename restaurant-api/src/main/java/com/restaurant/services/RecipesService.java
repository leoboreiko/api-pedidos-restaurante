package com.restaurant.services;

import com.restaurant.models.Recipes;
import com.restaurant.repositories.RecipesRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

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
    public Recipes save(Recipes r){
        return repository.save(r);
    }

    // Deletar receita
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
