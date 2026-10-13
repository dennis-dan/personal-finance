# Personal Finance Management System

A personal finance management project built with Java and Spring Boot, based on a microservices architecture. The application allows users to manage their expenses and income through two independent microservices, coordinated by a central orchestrator.

## Architecture

The project consists of three main components:

### 1. Finance Microservice

Responsible for managing personal expenses.

**Main features:**
- Retrieve all expenses.
- Retrieve an expense by ID.
- Create a new expense.
- Update an existing expense.
- Delete an expense.
- Store and retrieve expense data using MongoDB.

### 2. Income Microservice

Responsible for managing personal income.

**Main features:**
- Retrieve all income records.
- Retrieve an income record by ID.
- Create a new income record.
- Update an existing income record.
- Delete an income record.
- Store and retrieve income data using MongoDB.

### 3. Finance Orchestrator

Acts as the central entry point for client requests and coordinates communication with the two microservices.

**Main features:**
- Expose REST API endpoints for expenses and income.
- Forward HTTP requests to the appropriate microservice.
- Use Spring's `RestClient` for inter-service communication.
- Configure separate HTTP clients for each microservice.

## Technologies

- **Java**
- **Spring Boot**
- **Spring Web**
- **Spring Data MongoDB**
- **MongoDB**
- **REST APIs**
- **RestClient**
- **Maven**

## Application Ports

| Component | Port |
|---|---:|
| Finance Microservice | `8080` |
| Finance Orchestrator | `8081` |
| Income Microservice | `8082` |

## Configuration

The orchestrator uses the following properties to connect to the microservices:

```properties
server.port=8081

finance.microservice.url=http://localhost:8080
income.microservice.url=http://localhost:8082
```

Each microservice also requires its own MongoDB connection configuration.

## API Endpoints

The orchestrator exposes REST endpoints for managing expenses and income.

### Expenses

Base path: `/api/expenses`

| Method |        Endpoint      | Description               |
|--------|----------------------|---------------------------|
| GET    | `/api/expenses`      | Retrieve all expenses     |
| GET    | `/api/expenses/{id}` | Retrieve an expense by ID |
| POST   | `/api/expenses/save` | Create a new expense      |
| PUT    | `/api/expenses/{id}` | Update an expense         |
| DELETE | `/api/expenses/{id}` | Delete an expense         |

### Income

Base path: `/api/incomes`

| Method |        Endpoint     | Description                     |
|--------|---------------------|---------------------------------|
| GET    | `/api/incomes`      | Retrieve all income records     |
| GET    | `/api/incomes/{id}` | Retrieve an income record by ID |
| POST   | `/api/incomes/save` | Create a new income record      |
| PUT    | `/api/incomes/{id}` | Update an income record         |
| DELETE | `/api/incomes/{id}` | Delete an income record         |

*Note: The endpoints above describe the orchestrator's intended API structure. Check the implemented controller mappings if any paths differ.*

## Running the Project

1. Start MongoDB and ensure that the database configuration is correct.
2. Start the Finance Microservice on port `8080`.
3. Start the Income Microservice on port `8082`.
4. Start the Finance Orchestrator on port `8081`.
5. Use an HTTP client such as Bruno or Postman to send requests to the orchestrator.

Example request:

```http
GET http://localhost:8081/api/expenses
```

Example request for income:

```http
GET http://localhost:8081/api/incomes
```

## Project Goals

This project was developed as a practical learning experience to explore:

- Microservices architecture and separation of responsibilities.
- REST API development with Spring Boot.
- Communication between independent services using HTTP.
- MongoDB integration with Spring Data.
- Dependency injection and bean configuration.
- Centralized request routing through an orchestrator.

## Future Improvements

Potential future enhancements include:

- Calculate total income, total expenses, and the overall balance.
- Add filtering by category and date range.
- Improve validation and centralized error handling.
- Add automated unit and integration tests.
- Introduce Docker Compose for easier local setup.
- Add API documentation and authentication.

---

**Personal project developed for learning and experimentation with Java, Spring Boot, MongoDB, and microservices architecture.**
