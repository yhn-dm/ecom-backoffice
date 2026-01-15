package com.example.backend.controller;

import com.example.backend.config.SwaggerExamples;
import com.example.backend.dto.UserResponseDto;
import com.example.backend.entity.Category;
import com.example.backend.entity.Product;
import com.example.backend.entity.Role;
import com.example.backend.entity.User;
import com.example.backend.repository.CategoryRepository;
import com.example.backend.repository.ProductRepository;
import com.example.backend.repository.UserRepository;
import com.example.backend.dto.ProductRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.HashSet;


import java.util.List;


@Tag(name = "Admin")
@RestController
@RequestMapping("/api/admin")
@SecurityRequirement(name = "bearerAuth")
public class AdminController {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    public AdminController(
            ProductRepository productRepository,
            CategoryRepository categoryRepository,
            UserRepository userRepository
    ) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
    }


    @PostMapping("/products")
    @Operation(
            summary = "Créer un produit",
            description = "Création d’un produit (ADMIN uniquement)",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = @ExampleObject(value = SwaggerExamples.CREATE_PRODUCT)
                    )
            )
    )
    public Product createProduct(@RequestBody ProductRequestDto request) {
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        Product product = new Product();
        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @PutMapping("/products/{id}")
    @Operation(
            summary = "Modifier un produit",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            examples = @ExampleObject(value = SwaggerExamples.UPDATE_PRODUCT)
                    )
            )
    )
    public Product updateProduct(@PathVariable Long id, @RequestBody ProductRequestDto
            request) {
        Product product = productRepository.findById(id).orElseThrow();
        Category category = categoryRepository.findById(request.categoryId())
                .orElseThrow(() -> new RuntimeException("Category not found"));

        product.setName(request.name());
        product.setDescription(request.description());
        product.setPrice(request.price());
        product.setStockQuantity(request.stockQuantity());
        product.setCategory(category);

        return productRepository.save(product);
    }

    @DeleteMapping("/products/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    @GetMapping("/users")
    public List<UserResponseDto> getUsers() {
        return userRepository.findAll().stream().map(user -> {
            UserResponseDto dto = new UserResponseDto();
            dto.id = user.getId();
            dto.username = user.getUsername();
            dto.email = user.getEmail();
            dto.roles = user.getRoles();
            return dto;
        }).toList();
    }

    @PutMapping("/users/{id}/role")
    @Operation(
            summary = "Modifier le rôle d’un utilisateur",
            parameters = {
                    @Parameter(
                            name = "role",
                            example = "ADMIN",
                            description = "Valeur autorisée : USER ou ADMIN"
                    )
            }
    )
    public User updateRole(@PathVariable Long id, @RequestParam String role) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Role newRole;
        try {
            newRole = Role.valueOf(role);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid role: " + role);
        }

        user.getRoles().clear();
        user.getRoles().add(newRole);

        return userRepository.save(user);
    }

}
