package com.restaurant.models;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Entidade JPA para o usuário do sistema, implementando UserDetails para integração com Spring Security.
 */
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username; // Nome de usuário/login

    @Column(nullable = false)
    private String password; // Senha (hash)

    @Column(unique = true, nullable = false)
    private String email; // E-mail, também único

    // Usa uma coleção de Strings para as roles. É mais simples que uma tabela de relacionamento N:N completa.
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role")
    private Set<String> roles = new HashSet<>();

    // Construtores
    public User() {}
    
    public User(String username, String password, String email, Set<String> roles) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
    }

    // Implementação dos métodos da interface UserDetails
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Converte o Set de String de roles em uma Collection de GrantedAuthority
        return this.roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    // Getters e Setters obrigatórios (JPA e Spring Security)

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    
    // Getters e Setters padrão

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    // Os métodos abaixo retornam true por padrão, simplificando a implementação básica.
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}