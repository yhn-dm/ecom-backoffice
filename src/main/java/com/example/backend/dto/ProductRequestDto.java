package com.example.backend.dto;

public record ProductRequestDto(
        String name,
        String description,
        double price,
        int stockQuantity,
        Long categoryId
) {}
