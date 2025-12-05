package com.restaurant.services;

import com.restaurant.dtos.RegisterRequestDTO;
import com.restaurant.models.User;
import com.restaurant.repositories.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Set;

/**
 * Serviço responsável pela lógica de autenticação e registro de usuários.
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra um novo usuário no sistema.
     * 1. Verifica se o nome de usuário já existe.
     * 2. Codifica a senha.
     * 3. Define a role padrão ("ROLE_USER").
     * 4. Salva a nova entidade User no banco de dados.
     *
     * @param registerRequest Os dados de registro (username, password, email).
     * @return A entidade User salva.
     */
    public User registerNewUser(RegisterRequestDTO registerRequest) {
        // 1. Verificar se o username já existe
        if (userRepository.findByUsername(registerRequest.getUsername()).isPresent()) {
            // Em uma aplicação real, você lançaria uma exceção customizada aqui.
            throw new RuntimeException("Nome de usuário já está em uso!");
        }

        // 2. Criar a nova entidade User
        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        
        // 3. Codificar a senha antes de salvar (ESSENCIAL)
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));

        // 4. Atribuir uma role padrão
        Set<String> defaultRoles = Collections.singleton("ROLE_USER");
        user.setRoles(defaultRoles);

        // 5. Salvar no banco de dados
        return userRepository.save(user);
    }
}