package com.example.billingsoftware.BillingSoftwareBackend.config;

import com.example.billingsoftware.BillingSoftwareBackend.filters.JwtRequestFilter;
import com.example.billingsoftware.BillingSoftwareBackend.service.implementation.AppUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AppUserDetailsService appUserDetailsService;
    private final JwtRequestFilter jwtRequestFilter;

    @Value("${frontend.url}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http.cors(Customizer.withDefaults()) // enabling cors with default settings
                .csrf(AbstractHttpConfigurer::disable) // disabling csrf
                .authorizeHttpRequests(auth -> auth.requestMatchers("/login", "/encode", "/uploads/**").permitAll() // login is accessible by all users (admin, user or any other if available)
                        .requestMatchers("/category", "/categories", "/items", "/orders", "/payments", "/dashboard").hasAnyRole("USER", "ADMIN") // category and item is accessible by user and admin both
                        .requestMatchers("/admin/**").hasRole("ADMIN") //admin route is only accesscible by admin user
                        .anyRequest().authenticated()).sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))// managing session as stateless
                .addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); // adding jwtRequestFilter before UsernamePasswordAuthenticationFilter to ensure JWT validation happens before Spring Security's authentication process
        return http.build();
    }

    @Bean
    public CorsFilter corsFilter(){
        return new CorsFilter(corsConfigurationSource());
    }

    private org.springframework.web.cors.UrlBasedCorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(List.of(frontendUrl));
        config.setAllowedMethods(List.of("GET","POST","PUT","DELETE","OPTIONS","PATCH"));
        config.setAllowedHeaders(List.of("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source; // ✅ perfectly valid
    }

    @Bean
    public AuthenticationManager authenticationManager(){
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(appUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return new ProviderManager(authenticationProvider);
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }



}
