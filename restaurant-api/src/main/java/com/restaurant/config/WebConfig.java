package com.restaurant.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class WebConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        return http.cors(Customizer.withDefaults()).authorizeHttpRequests(conf ->
                conf.requestMatchers("/orders/**").permitAll().anyRequest().authenticated()          
        ).formLogin(Customizer.withDefaults()).build();  
    }       
}
