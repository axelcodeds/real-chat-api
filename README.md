# Real Chat API

A simple Spring Boot + MongoDB backend for a WhatsApp-like chat application. It is designed to be consumed by a React frontend and keeps the API intentionally small and beginner-friendly.

## Tech Stack

- Java 21
- Spring Boot
- Spring Web
- Spring Data MongoDB
- Lombok
- MongoDB

## What it provides

- User management
- Chat / conversation management
- Message sending and message history
- JSON REST endpoints for frontend integration

## Quick Start

### Prerequisites

- Java 21
- MongoDB running locally

### Run the application

```bash
./mvnw spring-boot:run
```

If you prefer building first:

```bash
./mvnw clean package
java -jar target/real-chat-api-0.0.1-SNAPSHOT.jar
```

## MongoDB Configuration

The app uses the MongoDB connection configured in `src/main/resources/application.properties`.

Default local URI:

```txt
mongodb://localhost:27017/real-chat
```

## API Base URL

```txt
http://localhost:8080/api
```

## Main Resources

- `/api/users`
- `/api/conversations`
- `/api/messages`

## Full API Documentation

For detailed endpoint definitions, request/response examples, frontend fetch samples, error handling, and CORS notes, see:

- [`API_DOCUMENTATION.md`](API_DOCUMENTATION.md)

## Notes

- No authentication is enabled yet.
- Responses are JSON on success and plain text on errors.
- The API is intentionally simple for learning and frontend integration.

