package com.dravinck.movies.config;


import jakarta.servlet.DispatcherType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // desabilita a proteção contra CSRF, em aplicações RESTful, onde o estado da sessão não é mantido no servidor. É MAIS CONFIAVEL
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(authorize -> authorize //usado para definir as regras de autorização para as requisições HTTP
                        .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                        //requestMatchers Especifica quais tipos de requisições e endpoints podem ser acessados com ou sem autenticação.
                        .requestMatchers(HttpMethod.POST, "/movies/auth/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/movies/auth/login").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/api-docs/**").permitAll()
                        .requestMatchers(HttpMethod.GET, "/swagger/**").permitAll()
                        .anyRequest().authenticated() //qualquer outra requisição precisa estar autenticada
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class )
                .build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
