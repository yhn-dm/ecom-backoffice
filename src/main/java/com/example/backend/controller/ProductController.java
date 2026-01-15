package com.example.backend.controller;

import com.example.backend.entity.Product;
import com.example.backend.repository.ProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Products")
@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @GetMapping
    @Operation(
            summary = "Lister les produits",
            description = "Liste paginée de tous les produits"
    )
    public Page<Product> getAll(
            @Parameter(example = "0", description = "Numéro de page")
            @RequestParam(defaultValue = "0") int page,

            @Parameter(example = "5", description = "Taille de page")
            @RequestParam(defaultValue = "5") int size
    ) {
        return productRepository.findAll(PageRequest.of(page, size));
    }


    @GetMapping("/{id}")
    @Operation(summary = "Détail d’un produit")
    public Product getById(
            @Parameter(example = "1")
            @PathVariable Long id
    ) {
        return productRepository.findById(id).orElseThrow();
    }

    @GetMapping("/search")
    @Operation(
            summary = "Recherche de produits",
            description = "Recherche par nom du produit"
    )
    public Page<Product> search(
            @Parameter(example = "iphone")
            @RequestParam(required = false) String name,

            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ) {
        if (name != null && !name.isBlank()) {
            return productRepository.findByNameContainingIgnoreCase(
                    name,
                    PageRequest.of(page, size)
            );

        }
        return productRepository.findAll(PageRequest.of(page, size));
    }

}
