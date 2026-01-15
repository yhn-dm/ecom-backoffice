package com.example.backend.seed;

import com.example.backend.entity.*;
import com.example.backend.repository.*;
import java.time.LocalDateTime;
import jakarta.annotation.PostConstruct;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;

@Component
public class DataSeeder {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public DataSeeder(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostConstruct
    public void seed() {

        Category electronics = categoryRepository
                .findByName("Electronique")
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setName("Electronique");
                    return categoryRepository.save(c);
                });

        Category books = categoryRepository
                .findByName("Livres")
                .orElseGet(() -> {
                    Category c = new Category();
                    c.setName("Livres");
                    return categoryRepository.save(c);
                });

        if (!productRepository.existsByName("iPhone")) {
            Product p = new Product();
            p.setName("iPhone");
            p.setDescription("Smartphone Apple");
            p.setPrice(999.0);
            p.setStockQuantity(10);
            p.setCategory(electronics);
            productRepository.save(p);
        }

        if (!userRepository.existsByUsername("admin")) {
            User admin = new User();
            admin.setUsername("admin");
            admin.setEmail("admin@test.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRoles(Set.of(Role.ADMIN));
            admin.setEnabled(true);
            admin.setCreatedAt(LocalDateTime.now());
            userRepository.save(admin);
        }
    }
}
