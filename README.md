# E-Commerce Application

This is a Spring Boot-based e-commerce application that provides functionalities for user registration, product management, order processing, and more. The application uses Spring Security for authentication and authorization, and integrates with a database for data persistence.

## Features

- User registration and authentication
- Role-based access control (Admin, Vendor, User)
- Product management (CRUD operations)
- Order processing
- Shopping cart and wishlist management
- Swagger API documentation

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- Hibernate
- PostgreSQL
- Swagger
- Lombok
- Maven

## Getting Started

### Prerequisites

- Java 23 
- Maven
- PostgreSQL
- 
### API Documentation
  The application uses Swagger for API documentation. Once the application is running, you can access the Swagger UI at:
  -http://localhost:9000/swagger-ui.html

### Endpoints

# Public Endpoints:
  -POST /api/signup/user - User registration
  -POST /api/signup/vendor - Vendor registration
  -POST /api/login - User login
# Admin Endpoints:
  -GET /admin/products - Get all products
  -POST /admin/products - Create a new product
  -PUT /admin/products/{id} - Update a product
  -DELETE /admin/products/{id} - Delete a product
# Vendor Endpoints:
  -GET /vendor/orders - Get all orders for the vendor
  -POST /vendor/products - Create a new product
  -PUT /vendor/products/{id} - Update a product
  -DELETE /vendor/products/{id} - Delete a product
# User Endpoints:
  -GET /user/cart - Get user's cart
  -POST /user/cart - Add a product to the cart
  -DELETE /user/cart/{productId} - Remove a product from the cart
  -GET /user/wishlist - Get user's wishlist
  -POST /user/wishlist - Add a product to the wishlist
  -DELETE /user/wishlist/{productId} - Remove a product from the wishlist
  -POST /user/orders - Create a new order

