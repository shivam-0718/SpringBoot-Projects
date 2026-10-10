# Customer Manager

A Spring Boot REST API for managing customers with basic CRUD operations. It uses DTOs to keep the JPA entity out of the API layer, validates incoming requests, and returns errors through a global exception handler.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Error Handling](#error-handling)

## Features
- Create, read, update and delete customers
- Request and response DTOs, so the client cannot set or change the `id`
- Entity ↔ DTO mapping in the service layer using Lombok `@Builder`
- Request validation with Jakarta Bean Validation (`@NotBlank`, `@Size`)
- Global exception handling with `@RestControllerAdvice` and proper HTTP status codes

## Tech Stack

### Technologies Used

**Backend Frameworks**
- Spring Boot
- Spring Data JPA

**Programming Language**
- Java

**Database**
- MySQL

**Build Tool**
- Maven

**Version Control System**
- Git
- GitHub

**API Testing Tool**
- Postman

### Dependencies Used
| Dependency | Dependency |
|---|---|
| spring-boot-starter-webmvc | spring-boot-starter-validation |
| spring-boot-starter-data-jpa | lombok |
| mysql-connector-j | spring-boot-devtools |
| spring-boot-starter-webmvc-test | spring-boot-starter-data-jpa-test |

**Versions:** JDK 21 · Spring Boot 4.1.1

## Project Structure
```
src/main/java/in/shivamvyas/customermanager
├── controller   # REST endpoints
├── service      # Business logic and DTO ↔ entity mapping
├── repository   # Spring Data JPA repository
├── entity       # Customer JPA entity
├── dto          # CustomerRequest, CustomerResponse
├── exception    # CustomerNotFoundException
└── advice       # Global exception handler, ErrorDetails
```

## Getting Started

### Prerequisites
- JDK 21
- MySQL running locally

### 1. Clone the repository
```bash
git clone https://github.com/shivam-0718/SpringBoot-Projects.git
cd SpringBoot-Projects/CustomerManager
```

### 2. Configure the database
Create a schema in MySQL:
```sql
CREATE DATABASE customer_db;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/customer_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```
The `customer` table is created automatically (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Run the application
```bash
./mvnw spring-boot:run
```
Or run `CustomerManagerApplication` from your IDE.

The API runs at: `http://localhost:8484/customer-manager/api/v1`

## API Endpoints

| Method | Endpoint | Description | Success Status |
|---|---|---|---|
| POST | `/customer` | Register a new customer | 201 Created |
| GET | `/customers` | Get all customers | 200 OK |
| GET | `/customers/{id}` | Get a customer by id | 200 OK |
| PUT | `/customers/{id}` | Update a customer | 200 OK |
| DELETE | `/customers/{id}` | Delete a customer | 200 OK |

### Request body (POST, PUT)
```json
{
  "firstName": "Shivam",
  "lastName": "Vyas",
  "city": "Mumbai"
}
```
Each field is required and must be 2–20 characters long.

### Response body
```json
{
  "id": 1,
  "firstName": "Shivam",
  "lastName": "Vyas",
  "city": "Mumbai"
}
```

## Error Handling

| Scenario | Status | Response |
|---|---|---|
| Customer not found | 404 Not Found | `{ "message": "...", "dateTime": "..." }` |
| Validation failure | 400 Bad Request | `{ "firstName": "First name is required" }` |
| Any other error | 500 Internal Server Error | `{ "message": "...", "dateTime": "..." }` |
