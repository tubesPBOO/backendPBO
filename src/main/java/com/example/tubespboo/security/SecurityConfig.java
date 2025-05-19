package com.example.tubespboo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.tubespboo.services.AuthServiceImpl;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private AppDetailsService userDetailsService;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, AuthServiceImpl authService) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(
                    "/api/customers/register", 
                    "/api/tukang/register", 
                    "/auth/**"
                ).permitAll()
                .requestMatchers("/api/admin/**").hasAuthority("ROLE_ADMIN")
                .requestMatchers("/api/customers/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CUSTOMER")
                .requestMatchers("/api/tukang/**").hasAuthority("ROLE_TUKANG")
                .requestMatchers("/api/tukang/getAll").hasAnyAuthority("ROLE_TUKANG", "ROLE_CUSTOMER")
                
                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults())
            .userDetailsService(userDetailsService)
            .addFilterBefore(customAuthenticationFilter(authService), UsernamePasswordAuthenticationFilter.class);
            
            

        return http.build();
    }

    @Bean
    public CustomAuthenticationFilter customAuthenticationFilter(AuthServiceImpl authService) {
        return new CustomAuthenticationFilter(authService);
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}