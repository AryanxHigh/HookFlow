# HookFlow — Reliable Webhook Delivery Platform

A Spring Boot-based webhook delivery platform that allows applications to register webhook subscriptions, publish events, deliver JSON payloads to subscribed endpoints, track delivery history, and automatically retry failed deliveries.

## Features

- Create and manage webhook subscriptions.
- Subscribe to specific event types.
- Publish events using REST APIs.
- Automatically deliver JSON payloads to matching subscriptions.
- Track webhook delivery history.
- View individual delivery details.
- Automatic retries with exponential backoff.
- Maximum of 4 delivery attempts.
- HTTP connection and read timeouts.
- Request validation and centralized exception handling.
- PostgreSQL database integration.

## Technology Stack

| Technology | Purpose |
|---|---|
| Java 21 | Programming language |
| Spring Boot 4.1.x | Backend framework |
| Spring Web MVC | REST APIs |
| Spring Data JPA | Database operations |
| PostgreSQL | Relational database |
| Hibernate | ORM |
| Maven | Build management |
| Postman | API testing |
| IntelliJ IDEA | Development environment |

## Project Architecture

```text
com.aryan.hookflow
│
├── config
├── controller
├── delivery
├── dto
├── entity
├── exception
├── repository
├── security
└── service
```

### Main Components

- **Controller:** Handles incoming REST API requests.
- **Service:** Contains webhook subscription, event publishing and delivery logic.
- **Repository:** Performs database operations.
- **Entity:** Represents webhook subscriptions and delivery records.
- **DTO:** Handles API request and response objects.
- **Exception:** Provides centralized error handling.

## Database Configuration

Create a PostgreSQL database named `hookflow`.

Configure `src/main/resources/application.properties`:

```properties
spring.application.name=hookflow

spring.datasource.url=jdbc:postgresql://localhost:5432/hookflow
spring.datasource.username=aryan
spring.datasource.password=${DB_PASSWORD}

spring.datasource.driver-class-name=org.postgresql.Driver

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
```

Set the `DB_PASSWORD` environment variable to your PostgreSQL password before running the application.

## Running the Project

1. Install Java 21.
2. Install PostgreSQL.
3. Create the `hookflow` database.
4. Configure the database credentials.
5. Open the project in IntelliJ IDEA.
6. Reload Maven dependencies.
7. Run `HookflowApplication.java`.

The application runs at:

`http://localhost:8080`

## REST API Documentation

### 1. Health Check

`GET /api/v1/health`

Checks whether the application is running.

### 2. Create Webhook Subscription

`POST /api/v1/webhooks`

Example request:

```json
{
  "targetUrl": "https://example.com/webhook",
  "eventType": "ORDER_CREATED"
}
```

### 3. Get All Subscriptions

`GET /api/v1/webhooks`

Returns all registered webhook subscriptions.

### 4. Publish Event

`POST /api/v1/events`

Example request:

```json
{
  "eventType": "ORDER_CREATED",
  "payload": {
    "orderId": 101,
    "customerName": "Aryan",
    "amount": 2500,
    "status": "CONFIRMED"
  }
}
```

The platform finds active subscriptions matching the event type and attempts delivery to their registered endpoints.

### 5. Get Delivery History

`GET /api/v1/deliveries`

Returns webhook delivery records.

### 6. Get Delivery by ID

`GET /api/v1/deliveries/{id}`

Returns details of a specific webhook delivery.

## Automatic Retry Mechanism

Failed webhook deliveries are automatically retried using exponential backoff.

| Attempt | Retry delay |
|---|---|
| 1 | 1 minute |
| 2 | 2 minutes |
| 3 | 4 minutes |
| 4 | No further retry |

The maximum number of delivery attempts is four, including the initial attempt.

## Delivery Statuses

- `PENDING` — Delivery created and awaiting processing.
- `SUCCESS` — Webhook successfully delivered.
- `FAILED` — Delivery attempt failed.

## Error Handling

The application provides centralized exception handling for invalid requests, malformed JSON and unexpected server errors.

## Future Improvements

- Background message queue integration.
- Distributed retry processing.
- Stronger SSRF and DNS-based destination protection.
- Webhook signature verification.
- Authentication and authorization.
- Rate limiting and monitoring.
- Delivery concurrency management.

## Author

**Aryan Kumar**

Java Backend Development | Spring Boot | PostgreSQL
