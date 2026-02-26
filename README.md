# E-commerce Back-Office API

[![Java 17](https://img.shields.io/badge/Java-17+-orange.svg)](https://openjdk.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.2.5-brightgreen.svg)](https://spring.io/projects/spring-boot)
[![Maven](https://img.shields.io/badge/Maven-3.x-blue.svg)](https://maven.apache.org/)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1.svg)](https://www.mysql.com/)
[![Swagger](https://img.shields.io/badge/OpenAPI-3.0-green.svg)](https://swagger.io/)

REST API for an e-commerce back-office: JWT-secured, documented with Swagger/OpenAPI, backed by MySQL. Handles auth, admin (users, roles, product CRUD), product catalog, and orders.

---

## Table of contents

- [What it is](#what-it-is)
- [Features](#features)
- [Tech stack](#tech-stack)
- [Prerequisites](#prerequisites)
- [Quick start](#quick-start)
- [Configuration](#configuration)
- [Running the app](#running-the-app)
- [API & authentication](#api--authentication)
- [Recommended request flow](#recommended-request-flow)
- [Project structure](#project-structure)
- [Screenshots](#screenshots)
- [Troubleshooting](#troubleshooting)

---

## What it is

This project is a **back-office e-commerce REST API** built with Spring Boot. It is intended as the backend for a storefront (web or mobile). It exposes endpoints for user registration and login, role-based admin (users, product CRUD), public product listing and search, and order creation and listing.

---

## Features

- **Authentication:** Register and login with JWT (Bearer token).
- **Admin:** List users, change user roles; full product CRUD (create, update, delete).
- **Catalog:** List products, get by ID, search (public endpoints).
- **Orders:** Create an order (authenticated), list my orders.
- **Documentation:** Interactive Swagger UI and raw OpenAPI spec.

---

## Tech stack

| Layer        | Technology                          |
|-------------|--------------------------------------|
| Language    | Java 17                              |
| Framework   | Spring Boot 3.2.5                    |
| Security    | Spring Security + JWT (jjwt 0.11.5)  |
| Persistence | Spring Data JPA, MySQL, Hibernate    |
| API docs    | springdoc-openapi (Swagger/OpenAPI 3)|
| Build       | Maven (wrapper included)             |

Architecture follows the usual Spring layout: **controllers** → **services** → **repositories** → **entities**, with **config** (Security, CORS, OpenAPI), **dto**, and **security** (JWT filter, user details).

---

## Prerequisites

- **Java 17+**
- **Maven** (or use the project’s `mvnw` / `mvnw.cmd`)
- **MySQL** (e.g. via XAMPP or any MySQL server)
- A browser (for Swagger UI)

---

## Quick start

1. **MySQL:** Create a database named `app_backend` (e.g. in phpMyAdmin: `http://localhost/phpmyadmin`). Recommended collation: `utf8mb4_general_ci`. No need to create tables — Hibernate creates them on first run (`spring.jpa.hibernate.ddl-auto=update`).
2. **Config:** If needed, adjust `src/main/resources/application.properties` (see [Configuration](#configuration)).
3. **Run:** From the project root:
   ```bash
   mvn spring-boot:run
   ```
4. **Try it:** Open [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html).

---

## Configuration

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/app_backend
spring.datasource.username=root
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQLDialect

server.port=8080
```

Change `username` and `password` if your MySQL setup differs.

---

## Running the app

From the project root:

```bash
mvn spring-boot:run
```

API base URL: **http://localhost:8080**

---

## API & authentication

- **Auth:** `POST /api/auth/register`, `POST /api/auth/login` (returns JWT).
- **Admin:** `GET /api/admin/users`, `PUT /api/admin/users/{id}/role`, `POST/PUT/DELETE /api/admin/products`.
- **Products (public):** `GET /api/products`, `GET /api/products/{id}`, `GET /api/products/search`.
- **Orders (authenticated):** `POST /api/orders`, `GET /api/orders/my-orders`.

Protected routes require a **Bearer token**. After login, copy the token and in Swagger use **Authorize** and set: `Bearer <your-token>`.

- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **OpenAPI JSON:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

---

## Recommended request flow

1. `POST /api/auth/register` — create a user  
2. `POST /api/auth/login` — get JWT  
3. (Admin) `GET /api/admin/users`, `PUT /api/admin/users/{id}/role`  
4. (Admin) `POST /api/admin/products`, then `PUT`/`DELETE` as needed  
5. `GET /api/products` or `/api/products/search` — browse catalog  
6. `POST /api/orders` — create order (with JWT)  
7. `GET /api/orders/my-orders` — list my orders (with JWT)

---

## Project structure

```
src/main/java/com/example/backend/
├── controller/   # Auth, Admin, Product, Order, Root
├── service/
├── repository/
├── entity/       # User, Role, Product, Category, Order, OrderStatus
├── dto/
├── config/       # Security, CORS, OpenAPI, exception handling
├── security/      # JWT filter, UserDetails, JwtUtil
└── seed/         # Optional data seeding
docs/screenshots/ # API/screen captures
```

---

## Screenshots

Example requests and responses are captured in `docs/screenshots/` (register, login, admin, products, orders).

---

## Troubleshooting

- **Port 8080 in use:** Change `server.port` in `application.properties` or stop the process using 8080.
- **MySQL connection refused:** Ensure MySQL is running (e.g. start MySQL in XAMPP). Check host, port (3306), and credentials.
- **Database/encoding:** Use `utf8mb4_general_ci` for the `app_backend` database to avoid encoding issues.
