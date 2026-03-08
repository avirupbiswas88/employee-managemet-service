# Employee Salary Management API

A RESTFul backend service built with **Java 17 and Spring Boot** that
manages employee records, calculates salary deductions based on
country-specific rules, and provides salary analytics.

## Technology Stack

-   Java 17
-   Spring Boot 3.2.5
-   Spring Data JPA
-   SQLite
-   Maven
-   Log4j2
-   Swagger / OpenAPI

## Features

### 1. Employee CRUD Endpoint

The system allows creation and management of employee records.

Each employee contains: - Full Name - Job Title - Country - Salary

All records are persisted in a **SQLite database**.

### CRUD APIs

-   **POST /employees** -- Create employee
-   **GET /employees/{id}** -- Retrieve employee
-   **GET /employees** -- Retrieve all employees
-   **PUT /employees/{id}** -- Update employee
-   **DELETE /employees/{id}** -- Delete employee

Example request:

``` json
{
  "fullName": "John Doe",
  "jobTitle": "Software Engineer",
  "country": "USA",
  "salary": 80000
}
```

------------------------------------------------------------------------

### 2. Salary Calculation Endpoint

Endpoint to retrieve salary breakdown for a specific employee.

    GET /employees/{id}/salary

### Deduction Rules

  Country           Deduction
  ----------------- ---------------------
  India             10% of gross salary
  United States     12% of gross salary
  Other Countries   No deduction

Formula:

    Deduction = Gross Salary × Country Tax Rate
    Net Salary = Gross Salary − Deduction

Example Response

``` json
{
  "id": 1,
  "grossSalary": 80000,
  "netSalary": 70400,
  "deduction": 9600
}
```

------------------------------------------------------------------------

### 3. Salary Metrics Endpoint

APIs providing salary analytics.

#### Salary statistics by country

    GET /employees/stats/salary/{country}

Returns: - Minimum salary - Maximum salary - Average salary

Example response

``` json
{
  "country": "USA",
  "minSalary": 60000,
  "maxSalary": 120000,
  "averageSalary": 85000,
  "responseMessage": "country specific employee salary stats found"
}
```

------------------------------------------------------------------------

#### Average salary by job title

    GET /employees/stats/average-salary/{jobTitle}

Example response

``` json
{
  "jobTitle": "Software Engineer",
  "averageSalary": 90000,
  "responseMessage": "average salary for job title found"
}
```

------------------------------------------------------------------------

## Architecture

Layered architecture:

    Controller Layer
        ↓
    Service Layer
        ↓
    Repository Layer
        ↓
    SQLite Database

### Controller Layer

Handles HTTP requests and responses.

### Service Layer

Contains business logic such as salary calculations and statistics.

### Repository Layer

Uses Spring Data JPA to interact with SQLite.

------------------------------------------------------------------------

## Project Structure

    src/main/java/com/employee
    │
    ├── controller
    │   ├── CrudController
    │   └── SalaryStatsController
    │
    ├── service
    │   ├── CrudOperationsService
    │   ├── CrudOperationsServiceImpl
    │   ├── SalaryStatisticsService
    │   └── SalaryStatisticsServiceImpl
    │
    ├── repository
    │   └── EmployeeRepository
    │
    ├── entity
    │   └── Employee
    │
    ├── dto
    │   ├── CrudOperationsRequestDTO
    │   ├── CrudOperationsResponseDTO
    │   ├── SalaryDetailsResponseDTO
    │   ├── AverageSalaryResponseDTO
    │   └── EmployeeStatsByCountryResponseDTO
    │
    ├── exception
    │   ├── EmployeeNotFoundException
    │   ├── JobTitleNotFoundException
    │   └── EmployeeServiceGenericException
    │
    └── util
        └── CountryTaxStrategy

------------------------------------------------------------------------

## Database Configuration

Example `application.properties`

    spring.datasource.url=jdbc:sqlite:employee.db
    spring.datasource.driver-class-name=org.sqlite.JDBC

    spring.jpa.hibernate.ddl-auto=update
    spring.jpa.show-sql=true

------------------------------------------------------------------------

## Running the Application

### Prerequisites

-   Java 17
-   Maven

### Build

    mvn clean install

### Run

    mvn spring-boot:run

Application runs at

    http://localhost:8080

------------------------------------------------------------------------

## API Documentation

Swagger UI available at:

    http://localhost:8080/swagger-ui.html

------------------------------------------------------------------------

## Logging

Logging is implemented using **Log4j2**.

Log levels used: - INFO - DEBUG - WARN - ERROR

------------------------------------------------------------------------

## Design Pattern

### Strategy Pattern

Implemented using `CountryTaxStrategy` for country-specific tax
calculations.

This allows: - Easy extension for new countries - Separation of tax
logic from service layer

------------------------------------------------------------------------

## Error Handling

Custom exceptions:

-   `EmployeeNotFoundException`
-   `JobTitleNotFoundException`
-   `EmployeeServiceGenericException`
-   `MissingRequiredDataException`

------------------------------------------------------------------------

# Employee Salary Management API – Implementation Prompts Guide

This document provides **structured prompts** that are used with AI coding assistants to build the **Employee Salary Management API** step-by-step using **Java 17 and Spring Boot**.

The prompts are organized according to a typical **backend development workflow**, allowing the system to be built incrementally.

----

# 1. Project Initialization Prompt

## Prompt

Create a Spring Boot REST API project using **Java 17 and Maven** for an Employee Salary Management system.

### Requirements

- Use **Spring Boot**
- Include dependencies:
  - Spring Web
  - Spring Data JPA
  - SQLite JDBC Driver
  - Log4j2
- Follow a **layered architecture**
- Configure Maven build
- Configure application properties

# 2. Employee Entity Creation Prompt

## Prompt

Create a JPA entity named **Employee** for an Employee Salary Management API.

### Requirements

Fields required:
- id (Primary Key)
- fullName
- jobTitle
- country
- grossSalary
- netSalary
- deduction

### Additional Requirements

- Use JPA annotations
- Table name: `employees`
- Auto-generate ID
- Include constructors, getters, setters

# 3. DTO Design Prompt

## Prompt

Create DTO classes to separate API payloads from database entities.

### Required DTO Classes

1. `CrudOperationsRequestDTO`
2. `CrudOperationsResponseDTO`
3. `SalaryDetailsResponseDTO`
4. `AverageSalaryResponseDTO`
5. `EmployeeStatsByCountryResponseDTO`

# 4. Repository Layer Prompt

## Prompt

Create a repository interface for managing Employee entities using Spring Data JPA.

### Requirements

- Interface name: `EmployeeRepository`
- Extend `JpaRepository<Employee, Long>`

# 5. Salary Strategy Design Prompt

## Prompt

Implement a **Strategy Pattern** for country-based tax deduction logic.

### Deduction Rules

| Country | Deduction |
| India | 10% |
| United States | 12% |
| Others | 0% |

# 6. CRUD Service Prompt

## Prompt

Implement a service class responsible for **Employee CRUD operations**.

### Requirements

- Map DTO to entity
- Calculate salary deductions
- Use CountryTaxStrategy
- Add logging
- Handle exceptions

# 7. Salary Analytics Service Prompt

## Prompt

Create a service responsible for calculating **salary statistics**.

### Required Features

1. Salary statistics by country
2. Average salary by job title

### Implementation Details

- Use `DoubleSummaryStatistics`
- Query database via repository
- Handle missing records
- Throw custom exceptions

# 8. CRUD Controller Prompt

## Prompt

Create a REST controller for managing employee records.

### Endpoints

| Method | Endpoint | Description |

POST | `/employees` | Create employee |
GET | `/employees/{id}` | Get employee by ID |
GET | `/employees` | Get all employees |
PUT | `/employees/{id}` | Update employee |
DELETE | `/employees/{id}` | Delete employee |
GET | `/employees/{id}/salary` | Get salary breakdown |

# 9. Salary Metrics Controller Prompt

## Prompt

Create a controller for salary analytics endpoints.

### Endpoints

| Method | Endpoint | Description |

GET | `/employees/stats/salary/{country}` | salary statistics by country |
GET | `/employees/stats/average-salary/{jobTitle}` | average salary by job title |

# 10. Exception Handling Prompt

## Prompt

Implement custom exceptions and global exception handling.

### Custom Exceptions

- `EmployeeNotFoundException`
- `JobTitleNotFoundException`
- `EmployeeServiceGenericException`

# 11. Swagger Documentation Prompt

## Prompt

Configure OpenAPI documentation for the project.

### Include

- API title
- API description
- Version
- Contact information

# 12. Testing Prompt

## Prompt

Write unit tests for the API.

### Frameworks

- JUnit
- Mockito

### Test Coverage

- CRUD operations
- Salary calculation logic
- Salary statistics logic
- Exception scenarios


## Future Improvements

Possible enhancements:

-   Pagination
-   Authentication and authorization
-   Docker support (CI/CD configs)
-   Integration testing
-   Caching salary analytics
