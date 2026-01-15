# Backend Spring Boot

API REST sécurisée pour une application e-commerce avec backoffice.

## Stack
- Java 17
- Spring Boot
- Spring Security + JWT
- Spring Data JPA
- MySQL

## Lancement
1. Créer la base MySQL : `app_backend`
2. Modifier `application.properties`
3. Lancer :
```bash
mvn spring-boot:run

Sécurité

JWT

Rôles : USER / ADMIN

Endpoints publics :

/api/auth/**

/api/products/**

Endpoints protégés :

/api/orders/**

/api/admin/**

Fonctionnalités

Authentification

Gestion utilisateurs

CRUD produits

Commandes

Backoffice admin