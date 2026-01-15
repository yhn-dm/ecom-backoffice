package com.example.backend.dto;

public record OrderRequestDto(
        Long productId,
        int quantity
) {}
