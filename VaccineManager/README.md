# Vaccine Manager

A Spring Boot REST API for managing vaccines with CRUD operations, bulk registration, search filters, dynamic projections and a few custom JPQL queries. It uses DTOs to keep the JPA entity out of the API layer, validates incoming requests, and returns errors through a global exception handler.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Error Handling](#error-handling)

## Features
- Create, read, update and delete vaccines
- Bulk registration of multiple vaccines in a single transaction
- Request and response DTOs, so the client cannot set or change the `id`
- Entity ↔ DTO mapping in the service layer using Lombok `@Builder`
- Request validation with Jakarta Bean Validation (`@NotBlank`, `@Size`, `@DecimalMin`, `@Digits`, `@PositiveOrZero`), including validation of each item in a list
- Custom finder methods (derived queries): search by name, company, and available doses range
- Dynamic projection with an interface view (`ResultView1`), so only the required columns are fetched
- Custom JPQL queries: low stock, search by disease, average cost, and a bulk cost update
- Global exception handling with `@RestControllerAdvice` and proper HTTP status codes

## Tech Stack

### Technologies Used

**Backend Frameworks**
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
- ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
- ![Hibernate](https://img.shields.io/badge/Hibernate-59666C?style=for-the-badge&logo=hibernate&logoColor=white)
- ![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge)

**Programming Language**
- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

**Database**
- ![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

**Build Tool**
- ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white)

**Version Control System**
- ![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=git&logoColor=white)
- ![GitHub](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=github&logoColor=white)

**API Testing Tool**
- ![Postman](https://img.shields.io/badge/Postman-FF6C37?style=for-the-badge&logo=postman&logoColor=white)

### Dependencies Used
| Dependency | Dependency |
|---|---|
| - spring-boot-starter-webmvc | - spring-boot-starter-validation |
| - spring-boot-starter-data-jpa | - lombok |
| - mysql-connector-j | - spring-boot-devtools |
| - spring-boot-starter-webmvc-test | - spring-boot-starter-data-jpa-test |

**Versions:** JDK 21 · Spring Boot 4.1.1

## Project Structure
```
src/main/java/in/shivamvyas/vaccinemanager
├── controller   # REST endpoints
├── service      # Business logic and DTO ↔ entity mapping
├── dao          # Spring Data JPA repository (finders and custom queries)
├── entity       # Vaccine JPA entity
├── dto          # VaccineRequest, VaccineResponse
├── view         # Projection interfaces (View, ResultView1)
├── exception    # VaccineNotFoundException
└── advice       # Global exception handler, ErrorDetails
```

## Getting Started

### Prerequisites
- JDK 21
- MySQL running locally

### 1. Clone the repository
```bash
git clone https://github.com/shivam-0718/SpringBoot-Projects.git
cd SpringBoot-Projects/VaccineManager
```

### 2. Configure the database
Create a schema in MySQL:
```sql
CREATE DATABASE vaccine_db;
```

Update `src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/vaccine_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```
The `Vaccines` table is created automatically (`spring.jpa.hibernate.ddl-auto=update`).

### 3. Run the application
```bash
./mvnw spring-boot:run
```
Or run `VaccineManagerApplication` from your IDE.

The API runs at: `http://localhost:8485/vaccine-manager/api/v1/vaccines`

## API Endpoints

All endpoints are under `/api/v1/vaccines`.

### CRUD and bulk

| Method | Endpoint | Description | Success Status |
|---|---|---|---|
| POST | `/` | Register a new vaccine | 201 Created |
| POST | `/bulk` | Register multiple vaccines (all or none are saved) | 201 Created |
| GET | `/` | Get all vaccines | 200 OK |
| GET | `/{id}` | Get a vaccine by id | 200 OK |
| PUT | `/{id}` | Update a vaccine | 200 OK |
| DELETE | `/{id}` | Delete a vaccine (returns a confirmation message) | 200 OK |

### Search and filters
These are query params on `GET /`. Spring picks the handler based on which params are present.

| Query params | Description | Success Status |
|---|---|---|
| `?vaccineName=covi` | Vaccines whose name contains the text (case-insensitive) | 200 OK |
| `?vaccineCompany=bharat` | Vaccines whose company contains the text (case-insensitive) | 200 OK |
| `?minDoses=10&maxDoses=100` | Vaccines with available doses in the range (inclusive) | 200 OK |
| `?vaccineName=covi&view=summary` | Name search returning only the fields of `ResultView1` (dynamic projection) | 200 OK |
| `?vaccineCompany=bharat&view=summary` | Company search returning only the fields of `ResultView1` | 200 OK |
| `?threshold=20` | Vaccines with available doses below the threshold, lowest stock first | 200 OK |
| `?disease=polio` | Vaccines whose `effectiveAgainst` contains the text (case-insensitive) | 200 OK |

### Statistics and bulk update

| Method | Endpoint | Description | Success Status |
|---|---|---|---|
| GET | `/stats/average-cost` | Average cost of all vaccines | 200 OK |
| PATCH | `/?vaccineName=Covaxin` | Update the cost of vaccines with the given name | 200 OK |

### Request body (POST, PUT, and each item in `/bulk`)
```json
{
  "vaccineName": "Covaxin",
  "vaccineCompany": "Bharat Biotech",
  "cost": 250.00,
  "availableDoses": 120,
  "effectiveAgainst": "COVID-19"
}
```

| Field | Rule |
|---|---|
| `vaccineName` | Required, 5–50 characters |
| `vaccineCompany` | Required, 5–50 characters |
| `cost` | Required, 0 or more, up to 10 digits and 2 decimal places |
| `availableDoses` | 0 or more |
| `effectiveAgainst` | Required, 2–20 characters |

`/bulk` takes a JSON array of the objects above.

### Response body
```json
{
  "id": 1,
  "vaccineName": "Covaxin",
  "vaccineCompany": "Bharat Biotech",
  "cost": 250.00,
  "availableDoses": 120
}
```

With `view=summary`, only the projection fields are returned:
```json
{
  "vaccineName": "Covaxin",
  "vaccineCompany": "Bharat Biotech",
  "availableDoses": "120"
}
```

### Bulk cost update (PATCH)
The request body is the new cost as a plain number, and the vaccine name is the query param:
```
PATCH /api/v1/vaccines?vaccineName=Covaxin
Body: 260.00
```
Response:
```json
{ "updatedCount": 1 }
```

## Error Handling

| Scenario | Status | Response |
|---|---|---|
| Vaccine not found | 404 Not Found | `{ "message": "...", "dateTime": "..." }` |
| Validation failure on a single request body | 400 Bad Request | `{ "vaccineName": "Vaccine Name should be between 5 and 50 characters." }` |
| Validation failure on an item in `/bulk` | 400 Bad Request | `{ "<parameter name>": "<message>" }` |
| Wrong parameter type (e.g. `/vaccines/abc`) | 400 Bad Request | `{ "message": "...", "dateTime": "..." }` |
| Any other error | 500 Internal Server Error | `{ "message": "...", "dateTime": "..." }` |
