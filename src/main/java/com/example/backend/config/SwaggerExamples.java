package com.example.backend.config;

public class SwaggerExamples {

    public static final String CREATE_PRODUCT = """
        {
          "name": "Produit Swagger",
          "description": "Produit créé via Swagger",
          "price": 29.99,
          "stockQuantity": 10,
          "categoryId": 1
        }
        """;

    public static final String UPDATE_PRODUCT = """
        {
          "name": "Produit modifié",
          "description": "Description modifiée",
          "price": 39.99,
          "stockQuantity": 5,
          "categoryId": 1
        }
        """;

    public static final String LOGIN = """
        {
          "username": "admin",
          "password": "admin123"
        }
        """;

    public static final String REGISTER = """
        {
          "username": "newuser",
          "email": "newuser@test.com",
          "password": "password123"
        }
        """;
}
