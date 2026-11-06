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
- Search expenses using multiple optional filters provided in the request body.
- Store and retrieve expense data using MongoDB.

### 2. Income Microservice

Responsible for managing personal income.

**Main features:**
- Retrieve all income records.
- Retrieve an income record by ID.
- Create a new income record.
- Update an existing income record.
- Delete an income record.
- Search income records using multiple optional filters provided in the request body.
- Store and retrieve income data using MongoDB.

### 3. Finance Orchestrator

Acts as the central entry point for client requests and coordinates communication with the two microservices.

**Main features:**
- Expose REST API endpoints for expenses and income.
- Forward HTTP requests to the appropriate microservice.
- Forward search requests, including their filter parameters, to the corresponding microservice.
- Use Spring's `RestClient` for inter-service communication.
- Configure separate HTTP clients for each microservice.
- Provide a monthly financial summary by calculating the difference between total income and total expenses for a specified month and year.
- Retrieve the required data from both microservices to calculate the monthly balance.

## Technologies

- **Java 21**
- **Spring Boot**
- **Spring Web**
- **Spring Data MongoDB**
- **MongoDB**
- **REST APIs**
- **RestClient**
- **Maven**
- **Docker**
- **Docker Compose**
- **WSL 2**

## Application Ports

| Component | Port |
|---|---:|
| Finance Microservice | `8080` |
| Finance Orchestrator | `8081` |
| Income Microservice | `8082` |
| MongoDB | `27017` |

## Configuration

The orchestrator uses the following properties to connect to the microservices:

```properties
server.port=8081

finance.microservice.url=http://localhost:8080
income.microservice.url=http://localhost:8082
```

Each microservice requires its own MongoDB connection configuration.

The Finance Microservice supports an environment variable to configure its MongoDB connection:

```properties
spring.mongodb.uri=${MONGODB_URI:mongodb://localhost:27017/finance-db}
```

This allows the application to use different MongoDB connection addresses depending on the execution environment.

When running through Docker Compose, the Finance Microservice connects to MongoDB using the Docker service name instead of `localhost`.

## API Endpoints

The orchestrator exposes REST endpoints for managing expenses and income.

### Expenses

Base path: `/api/expenses`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/expenses` | Retrieve all expenses |
| GET | `/api/expenses/{id}` | Retrieve an expense by ID |
| POST | `/api/expenses/save` | Create a new expense |
| PUT | `/api/expenses/{id}` | Update an expense |
| DELETE | `/api/expenses/{id}` | Delete an expense |
| POST | `/api/expenses/search` | Search expenses using optional filters in the request body |

### Income

Base path: `/api/incomes`

| Method | Endpoint | Description |
|---|---|---|
| GET | `/api/incomes` | Retrieve all income records |
| GET | `/api/incomes/{id}` | Retrieve an income record by ID |
| POST | `/api/incomes/save` | Create a new income record |
| PUT | `/api/incomes/{id}` | Update an income record |
| DELETE | `/api/incomes/{id}` | Delete an income record |
| POST | `/api/incomes/search` | Search income records using optional filters in the request body |

### Monthly Financial Summary

The orchestrator also provides an endpoint to calculate the monthly financial balance.

The request requires two query parameters:

- `month`: the month to analyze.
- `year`: the year to analyze.

Example:

```http
GET http://localhost:8081/api/summary?month=11&year=2026
```

The operation retrieves the income and expense records for the requested period and calculates:

```text
Monthly Balance = Total Income - Total Expenses
```

The result represents the financial difference between incoming and outgoing money for the selected month.

*Note: Check the implemented controller mappings if any endpoint paths differ from the examples above.*

## Running the Project

The project can be executed locally or by using Docker for containerized services.

### 1. Prerequisites

Before running the project, install the following tools.

#### Java 21

Install Java Development Kit (JDK) 21 if you intend to run the microservices locally.

#### Git

Git is required to clone the repository.

Official website: https://git-scm.com/

#### WSL 2

WSL (Windows Subsystem for Linux) is required for the standard Docker Desktop WSL 2 backend on Windows.

Open PowerShell as administrator and execute:

```powershell
wsl --install
```

Restart the computer if requested.

To verify the installation:

```powershell
wsl --list --verbose
```

The Linux distribution should use WSL version 2.

Official documentation: https://learn.microsoft.com/en-us/windows/wsl/install

#### Docker Desktop

Download and install Docker Desktop for Windows.

Docker Desktop provides the Docker Engine and Docker Compose required to build and run the containers.

Official website: https://www.docker.com/products/docker-desktop/

After installation:
1. Start Docker Desktop.
2. Make sure the Docker Engine is running.
3. If necessary, enable the WSL 2 based engine in Docker Desktop settings.

#### Bruno or another HTTP client

An HTTP client is required to send requests to the REST APIs.

Recommended: Bruno.

Download: https://www.usebruno.com/

Alternatives include Postman and Insomnia.

### 2. Clone the repository

Open PowerShell and navigate to the directory where you want to download the project.

Execute:

```powershell
git clone https://github.com/dennis-dan/personal-finance.git
```

Enter the project directory:

```powershell
cd personal-finance
```

### 3. Start Docker and build the containers

Make sure Docker Desktop is running.

From the project root directory, where `compose.yaml` is located, execute:

```powershell
docker compose up --build -d
```

Docker Compose will:
- Download the required base images if they are not already available locally.
- Build the Finance Microservice Docker image.
- Download the MongoDB image.
- Create and start the MongoDB container.
- Create and start the Finance Microservice container.
- Configure communication between the Finance Microservice and MongoDB.

The `-d` option runs the containers in the background.

To check that the containers are running:

```powershell
docker ps
```

To inspect the Finance Microservice logs:

```powershell
docker logs finance-container
```

### 4. Start the remaining microservices

In the current Docker Compose configuration, MongoDB and the Finance Microservice are containerized.

The Income Microservice and Finance Orchestrator must still be started separately, for example through an IDE such as Eclipse or by using Maven.

Start:
1. Income Microservice on port `8082`.
2. Finance Orchestrator on port `8081`.

Make sure their MongoDB and microservice URL configurations match the selected execution environment.

### 5. Send HTTP requests

Once the required services are running, open Bruno or another HTTP client.

The orchestrator is accessible at:

```text
http://localhost:8081
```

Example requests:

Retrieve all expenses:

```http
GET http://localhost:8081/api/expenses
```

Retrieve all income records:

```http
GET http://localhost:8081/api/incomes
```

Search expenses:

```http
POST http://localhost:8081/api/expenses/search
Content-Type: application/json
```

Search income records:

```http
POST http://localhost:8081/api/incomes/search
Content-Type: application/json
```

For search requests, provide the desired optional filters in the JSON request body according to the corresponding search request DTO.

Calculate the monthly financial summary:

```http
GET http://localhost:8081/api/summary?month=11&year=2026
```

### 6. Stop the Docker containers

To stop and remove the containers and the Docker Compose network, execute:

```powershell
docker compose down
```

The MongoDB data is stored in a named Docker volume, so it is preserved when the containers are stopped or removed.

To also delete the stored database volume, use:

```powershell
docker compose down -v
```

**Warning:** The `-v` option permanently removes the MongoDB volume and its stored data.

## Docker and Portability

Docker makes it possible to run the containerized parts of the application in a consistent environment across different computers.

The project includes:
- A Dockerfile for building the Finance Microservice image.
- A Docker Compose configuration for starting the Finance Microservice and MongoDB together.
- A named Docker volume to persist MongoDB data across container restarts and recreation.
- Environment-based MongoDB connection configuration.

After cloning the repository, another computer can build and start the configured containers using:

```powershell
docker compose up --build -d
```

Docker automatically downloads the required images and builds the application image.

**Important:** GitHub stores the source code and configuration, not the contents of the local MongoDB Docker volume. Database records are not automatically transferred between computers. The current Compose configuration also does not yet containerize the Income Microservice and Finance Orchestrator.

## Project Goals

This project was developed as a practical learning experience to explore:

- Microservices architecture and separation of responsibilities.
- REST API development with Spring Boot.
- Dynamic search operations using multiple optional request parameters.
- Communication between independent services using HTTP.
- MongoDB integration with Spring Data.
- Dependency injection and bean configuration.
- Centralized request routing through an orchestrator.
- Aggregation of data from multiple microservices.
- Financial calculations using income and expense records.
- Containerization with Docker and orchestration with Docker Compose.
- Database persistence through Docker volumes.
- Running applications in different development environments.

## Future Improvements

Potential future enhancements include:

- Add automated unit and integration tests.
- Improve validation and centralized error handling.
- Add API documentation with Swagger/OpenAPI.
- Introduce authentication and user management.
- Containerize the Income Microservice and Finance Orchestrator and include them in Docker Compose.
- Evaluate a cloud-hosted MongoDB solution to share data across different computers.

---

**Personal project developed for learning and experimentation with Java, Spring Boot, MongoDB, microservices architecture, and Docker.**
