# Weather API

A Spring Boot REST API that returns the current weather for a city by calling the third-party [WeatherAPI.com](https://www.weatherapi.com/) service. It uses separate DTOs for the provider and for our own response, reads its settings from validated typed properties, and returns every failure through a global exception handler in one consistent JSON shape.

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
- [API Endpoints](#api-endpoints)
- [Error Handling](#error-handling)

## Features
- Get the current weather for any city WeatherAPI.com can find
- Optional air quality details (`includeAqi=true`) with a readable US EPA category
- Provider DTOs kept separate from our response DTOs, so the provider's format never reaches the client
- Typed configuration with `@ConfigurationProperties`, validated with Bean Validation so the app fails fast at startup
- Connect and read timeouts on every call to the provider
- Provider errors (unknown city, bad key, outage, timeout) translated into proper HTTP statuses
- Request validation on the `city` parameter
- Global exception handling with `@RestControllerAdvice` and one error response shape
- API key kept out of Git through an environment variable or a git-ignored `local` profile

## Tech Stack

### Technologies Used

**Backend Frameworks**
- ![Spring Boot](https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
- ![Spring](https://img.shields.io/badge/Spring-6DB33F?style=for-the-badge&logo=spring&logoColor=white)
- ![Lombok](https://img.shields.io/badge/Lombok-BC4521?style=for-the-badge)

**Programming Language**
- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)

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
| - spring-boot-starter-web | - lombok |
| - spring-boot-starter-webmvc-test | |

**Versions:** JDK 21 · Spring Boot 4.1.1

## Project Structure
```
src/main/java/in/shivamvyas/weather
├── WeatherApplication.java   # Entry point, @ConfigurationPropertiesScan
├── controller                # REST endpoint
├── service                   # Business logic and provider DTO → response DTO mapping
├── client                    # WeatherApiClient, the only class that calls the provider
├── config                    # WeatherApiProperties, WeatherClientConfig (RestClient bean)
├── dto                       # WeatherResponse, AirQualityInfo (our response)
│   └── provider              # WeatherApiResponse, Location, Current, Condition, AirQuality, WeatherApiError
├── exception                 # CityNotFoundException, WeatherApiException
└── advice                    # Global exception handler, ErrorResponse, ErrorDetails
```

## Getting Started

### Prerequisites
- JDK 21
- A free API key from [WeatherAPI.com](https://www.weatherapi.com/)

### 1. Clone the repository
```bash
git clone https://github.com/shivam-0718/SpringBoot-Projects.git
cd SpringBoot-Projects/Weather
```

### 2. Configure the API key
`src/main/resources/application.properties` reads the key from an environment variable:
```properties
weather.api.base-url=https://api.weatherapi.com/v1
weather.api.key=${WEATHER_API_KEY}
weather.api.timeout=10s
```

Choose one way to provide the key. Never commit a real key.

**Option A: environment variable**
```powershell
# Windows PowerShell
$env:WEATHER_API_KEY="your_key_here"
```
```bash
# macOS / Linux
export WEATHER_API_KEY=your_key_here
```
Restart your IDE or terminal after changing it, otherwise the app keeps the old value.

**Option B: local profile (git-ignored)**

Create `src/main/resources/application-local.properties`:
```properties
weather.api.key=your_key_here
```
This file is listed in `.gitignore`. Run the app with the `local` profile (IntelliJ: Active profiles = `local`).

The app refuses to start if the base URL, key or timeout is missing.

### 3. Run the application
```bash
./mvnw spring-boot:run
```
With the local profile:
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=local
```
Or run `WeatherApplication` from your IDE.

The API runs at: `http://localhost:8486/weather-app/api/v1/weather`

## API Endpoints

All endpoints are under `/weather-app/api/v1/weather`.

| Method | Endpoint | Description | Success Status |
|---|---|---|---|
| GET | `?city=Hyderabad` | Current weather for a city | 200 OK |
| GET | `?city=Hyderabad&includeAqi=true` | Current weather with air quality details | 200 OK |

### Query parameters

| Parameter | Type | Required | Default | Description |
|---|---|---|---|---|
| `city` | string | Yes | none | City name. Must not be blank. |
| `includeAqi` | boolean | No | `false` | Adds the `airQualityInfo` block when `true` |

### Response body
```json
{
  "city": "Hyderabad",
  "region": "Telangana",
  "country": "India",
  "localTime": "2026-10-17 13:51",
  "temperatureCelsius": 33.3,
  "feelsLikeCelsius": 33.7,
  "condition": "Cloudy",
  "iconUrl": "https://cdn.weatherapi.com/weather/64x64/day/119.png",
  "humidity": 38,
  "windKmph": 10.4,
  "windDirection": "NE",
  "uvIndex": 6.0
}
```

With `includeAqi=true`, this is added:
```json
{
  "airQualityInfo": {
    "pm25": 24.4,
    "pm10": 31.7,
    "usEpaCategory": "Moderate"
  }
}
```
When air quality is not requested, `airQualityInfo` is left out instead of being returned as `null`.

### US EPA categories

| Index | Category |
|---|---|
| 1 | Good |
| 2 | Moderate |
| 3 | Unhealthy for sensitive groups |
| 4 | Unhealthy |
| 5 | Very Unhealthy |
| 6 | Hazardous |

## Error Handling

Every error uses the same body:
```json
{
  "timestamp": "2026-10-17T14:30:12.481",
  "status": 404,
  "error": "Not Found",
  "message": "City not found: asdfgh"
}
```

| Scenario | Provider response | Status |
|---|---|---|
| City not found | HTTP 400, code 1006 | 404 Not Found |
| API key invalid, disabled or quota exceeded | HTTP 4xx, codes 2006 to 2008 | 502 Bad Gateway |
| Any other provider 4xx error | HTTP 4xx | 502 Bad Gateway |
| Provider is down | HTTP 5xx | 502 Bad Gateway |
| Provider unreachable or timed out | No response | 502 Bad Gateway |
| Blank `city` | Not called | 400 Bad Request |
| Any other error | Not involved | 500 Internal Server Error |

Validation failures add a `details` map:
```json
{
  "timestamp": "2026-10-17T14:30:40.102",
  "status": 400,
  "error": "Bad Request",
  "message": "Validation failed",
  "details": { "city": "City cannot be blank" }
}
```

The 500 response always has a generic message. The full stack trace is only written to the server log.
