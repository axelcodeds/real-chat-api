# API Documentation

## Project Overview

This project is a simple chat backend inspired by WhatsApp Web. It provides REST endpoints for managing users, conversations, and messages.

### What this API does
- Creates and manages users
- Creates and lists conversations between users
- Sends messages inside a conversation
- Returns chat history in chronological order

### Main purpose of the chat system
The API is designed to support a React 19 frontend that needs a lightweight backend for a real-time-style chat experience. The backend stores chat data in MongoDB and exposes simple JSON endpoints that are easy to consume from the browser.

### Technologies used
- Java 21
- Spring Boot
- Spring Web
- Spring Data MongoDB
- Lombok
- MongoDB

### REST API conventions
- JSON over HTTP
- Resource-oriented endpoints
- Base path starts with `/api`
- Uses standard HTTP methods:
  - `GET` for reading data
  - `POST` for creating data
  - `PUT` for updating data
  - `DELETE` for removing data

> Note: This backend currently returns raw DTOs on success and plain text on error responses. There is no global response envelope implemented yet.

---

# Base URL

```txt
http://localhost:8080/api
```

---

# Authentication

```txt
Currently this API does not require authentication.
```

Frontend developers can call the endpoints directly without sending access tokens, cookies, or authorization headers.

---

# General Response Format

This backend does **not** currently wrap every response in a common `success/message/data` envelope. Instead, it returns:

- **Success responses:** raw DTOs or arrays of DTOs
- **Error responses:** plain text messages

### Optional client-side convention
If you want a consistent frontend state shape, you can normalize API responses in the React app using a wrapper like this:

```json
{
  "success": true,
  "message": "Request completed successfully",
  "data": {}
}
```

### Example error wrapper for frontend state

```json
{
  "success": false,
  "message": "Resource not found"
}
```

### Actual API behavior
A typical successful response from this backend looks like this:

```json
{
  "id": "6636f3c2a7f1b42c9e4d1a10",
  "username": "Axel",
  "email": "axel@example.com"
}
```

A typical error response looks like this:

```txt
User not found with id: 6636f3c2a7f1b42c9e4d1a10
```

---

# Content Type

All request bodies and JSON responses use:

```txt
Content-Type: application/json
```

The API uses UTF-8 encoded JSON payloads.

---

# DTOs / Entities

This section documents the main request and response objects used by the API.

## User DTO

### `UserDto`

Used for creating, updating, and returning user data.

| Field | Type | Description |
|---|---|---|
| `id` | `String` | MongoDB document identifier |
| `username` | `String` | Display name of the user |
| `email` | `String` | User email address (must be unique) |

### Example JSON

```json
{
  "id": "6636f3c2a7f1b42c9e4d1a10",
  "username": "Axel",
  "email": "axel@example.com"
}
```

---

## Chat DTO / Conversation DTO

In this project, **chat** and **conversation** mean the same thing.

### `ConversationDto`

Used to return chat metadata to the frontend.

| Field | Type | Description |
|---|---|---|
| `id` | `String` | MongoDB document identifier |
| `participantIds` | `List<String>` | User IDs included in the chat |
| `lastMessage` | `String` | Most recent message content |
| `lastMessageAt` | `Instant` | Timestamp of the latest message |

### Example JSON

```json
{
  "id": "6636f43fa7f1b42c9e4d1a11",
  "participantIds": ["6636f3c2a7f1b42c9e4d1a10", "6636f3e7a7f1b42c9e4d1a12"],
  "lastMessage": "See you tomorrow",
  "lastMessageAt": "2026-05-07T14:30:12.123Z"
}
```

---

## Message DTO

### `MessageDto`

Used to return chat messages.

| Field | Type | Description |
|---|---|---|
| `id` | `String` | MongoDB document identifier |
| `conversationId` | `String` | Chat/conversation ID |
| `senderId` | `String` | User ID of the sender |
| `content` | `String` | Message text |
| `createdAt` | `Instant` | Time the message was created |

### Example JSON

```json
{
  "id": "6636f4b1a7f1b42c9e4d1a13",
  "conversationId": "6636f43fa7f1b42c9e4d1a11",
  "senderId": "6636f3c2a7f1b42c9e4d1a10",
  "content": "Hello, are you online?",
  "createdAt": "2026-05-07T14:31:45.456Z"
}
```

---

## Create User Request

The API reuses `UserDto` for create and update operations.

### Example JSON

```json
{
  "username": "Axel",
  "email": "axel@example.com"
}
```

---

## Create Conversation Request

### `CreateConversationRequest`

| Field | Type | Description |
|---|---|---|
| `participantIds` | `List<String>` | IDs of the users included in the conversation |

### Example JSON

```json
{
  "participantIds": ["6636f3c2a7f1b42c9e4d1a10", "6636f3e7a7f1b42c9e4d1a12"]
}
```

---

## Create Message Request

### `CreateMessageRequest`

| Field | Type | Description |
|---|---|---|
| `senderId` | `String` | ID of the user sending the message |
| `recipientId` | `String` | ID of the other participant |
| `content` | `String` | Message body |

### Example JSON

```json
{
  "senderId": "6636f3c2a7f1b42c9e4d1a10",
  "recipientId": "6636f3e7a7f1b42c9e4d1a12",
  "content": "Hello world"
}
```

---

# Endpoints

## Users

### `POST /users`

Creates a new user.

**HTTP Method:** `POST`

**URL:** `/api/users`

**Description:** Creates a user record in MongoDB and returns the created user.

**Path Variables:** None

**Query Params:** None

**Request Body:** `UserDto` without `id`

**Response Body:** `UserDto`

**Example Request**

```json
{
  "username": "Axel",
  "email": "axel@example.com"
}
```

**Example Response**

```json
{
  "id": "6636f3c2a7f1b42c9e4d1a10",
  "username": "Axel",
  "email": "axel@example.com"
}
```

**Possible Status Codes**
- `201 Created`
- `400 Bad Request`
- `409 Conflict`
- `500 Internal Server Error`

---

### `GET /users/{id}`

Returns a user by ID.

**HTTP Method:** `GET`

**URL:** `/api/users/{id}`

**Description:** Fetches a single user from MongoDB.

**Path Variables:**
- `id` — user ID

**Query Params:** None

**Request Body:** None

**Response Body:** `UserDto`

**Example Response**

```json
{
  "id": "6636f3c2a7f1b42c9e4d1a10",
  "username": "Axel",
  "email": "axel@example.com"
}
```

**Possible Status Codes**
- `200 OK`
- `404 Not Found`
- `500 Internal Server Error`

---

### `GET /users/email/{email}`

Returns a user by email.

**HTTP Method:** `GET`

**URL:** `/api/users/email/{email}`

**Description:** Fetches a single user using the email field.

**Path Variables:**
- `email` — user email (URL-encoded, for example `axel%40example.com`)

**Query Params:** None

**Request Body:** None

**Response Body:** `UserDto`

**Example Response**

```json
{
  "id": "6636f3c2a7f1b42c9e4d1a10",
  "username": "Axel",
  "email": "axel@example.com"
}
```

**Possible Status Codes**
- `200 OK`
- `404 Not Found`
- `500 Internal Server Error`

---

### `GET /users`

Returns all users.

**HTTP Method:** `GET`

**URL:** `/api/users`

**Description:** Returns a list of all users.

**Path Variables:** None

**Query Params:** None

**Request Body:** None

**Response Body:** `UserDto[]`

**Example Response**

```json
[
  {
    "id": "6636f3c2a7f1b42c9e4d1a10",
    "username": "Axel",
    "email": "axel@example.com"
  },
  {
    "id": "6636f3e7a7f1b42c9e4d1a12",
    "username": "Maya",
    "email": "maya@example.com"
  }
]
```

**Possible Status Codes**
- `200 OK`
- `500 Internal Server Error`

---

### `PUT /users/{id}`

Updates an existing user.

**HTTP Method:** `PUT`

**URL:** `/api/users/{id}`

**Description:** Updates the username and email of an existing user.

**Path Variables:**
- `id` — user ID

**Query Params:** None

**Request Body:** `UserDto`

**Response Body:** `UserDto`

**Example Request**

```json
{
  "username": "Axel Diego",
  "email": "axel.diego@example.com"
}
```

**Example Response**

```json
{
  "id": "6636f3c2a7f1b42c9e4d1a10",
  "username": "Axel Diego",
  "email": "axel.diego@example.com"
}
```

**Possible Status Codes**
- `200 OK`
- `400 Bad Request`
- `404 Not Found`
- `409 Conflict`
- `500 Internal Server Error`

---

### `DELETE /users/{id}`

Deletes a user.

**HTTP Method:** `DELETE`

**URL:** `/api/users/{id}`

**Description:** Removes a user from MongoDB.

**Path Variables:**
- `id` — user ID

**Query Params:** None

**Request Body:** None

**Response Body:** None

**Possible Status Codes**
- `204 No Content`
- `404 Not Found`
- `500 Internal Server Error`

---

## Conversations

### `POST /conversations`

Creates a new conversation.

**HTTP Method:** `POST`

**URL:** `/api/conversations`

**Description:** Creates a chat using the provided participant IDs. If a conversation with the same participant list already exists, the service returns that conversation instead of creating a duplicate.

**Path Variables:** None

**Query Params:** None

**Request Body:** `CreateConversationRequest`

**Response Body:** `ConversationDto`

**Example Request**

```json
{
  "participantIds": ["6636f3c2a7f1b42c9e4d1a10", "6636f3e7a7f1b42c9e4d1a12"]
}
```

**Example Response**

```json
{
  "id": "6636f43fa7f1b42c9e4d1a11",
  "participantIds": ["6636f3c2a7f1b42c9e4d1a10", "6636f3e7a7f1b42c9e4d1a12"],
  "lastMessage": null,
  "lastMessageAt": null
}
```

**Possible Status Codes**
- `201 Created`
- `400 Bad Request`
- `500 Internal Server Error`

---

### `GET /conversations/{id}`

Returns a conversation by ID.

**HTTP Method:** `GET`

**URL:** `/api/conversations/{id}`

**Description:** Fetches a single conversation.

**Path Variables:**
- `id` — conversation ID

**Query Params:** None

**Request Body:** None

**Response Body:** `ConversationDto`

**Example Response**

```json
{
  "id": "6636f43fa7f1b42c9e4d1a11",
  "participantIds": ["6636f3c2a7f1b42c9e4d1a10", "6636f3e7a7f1b42c9e4d1a12"],
  "lastMessage": "See you tomorrow",
  "lastMessageAt": "2026-05-07T14:30:12.123Z"
}
```

**Possible Status Codes**
- `200 OK`
- `404 Not Found`
- `500 Internal Server Error`

---

### `GET /conversations/user/{userId}`

Returns all conversations for a specific user.

**HTTP Method:** `GET`

**URL:** `/api/conversations/user/{userId}`

**Description:** Returns all conversations where the user ID is present in `participantIds`.

**Path Variables:**
- `userId` — user ID

**Query Params:** None

**Request Body:** None

**Response Body:** `ConversationDto[]`

**Example Response**

```json
[
  {
    "id": "6636f43fa7f1b42c9e4d1a11",
    "participantIds": ["6636f3c2a7f1b42c9e4d1a10", "6636f3e7a7f1b42c9e4d1a12"],
    "lastMessage": "See you tomorrow",
    "lastMessageAt": "2026-05-07T14:30:12.123Z"
  },
  {
    "id": "6636f4b9a7f1b42c9e4d1a14",
    "participantIds": ["6636f3c2a7f1b42c9e4d1a10", "6636f4a0a7f1b42c9e4d1a15"],
    "lastMessage": "Lunch at 1?",
    "lastMessageAt": "2026-05-07T15:05:01.000Z"
  }
]
```

**Possible Status Codes**
- `200 OK`
- `500 Internal Server Error`

---

## Messages

### `POST /messages`

Sends a message.

**HTTP Method:** `POST`

**URL:** `/api/messages`

**Description:** Sends a new message. The backend checks whether a conversation already exists between the sender and recipient. If it does not exist, the backend creates it automatically, saves the message, and updates the conversation preview fields (`lastMessage` and `lastMessageAt`).

**Path Variables:** None

**Query Params:** None

**Request Body:** `CreateMessageRequest`

**Response Body:** `MessageDto`

**Example Request**

```json
{
  "senderId": "6636f3c2a7f1b42c9e4d1a10",
  "recipientId": "6636f3e7a7f1b42c9e4d1a12",
  "content": "Hello world"
}
```

**Example Response**

```json
{
  "id": "6636f4b1a7f1b42c9e4d1a13",
  "conversationId": "6636f43fa7f1b42c9e4d1a11",
  "senderId": "6636f3c2a7f1b42c9e4d1a10",
  "content": "Hello world",
  "createdAt": "2026-05-07T15:10:45.456Z"
}
```

**Possible Status Codes**
- `201 Created`
- `400 Bad Request`
- `500 Internal Server Error`

---

### `GET /messages/conversation/{conversationId}`

Returns all messages in a conversation.

**HTTP Method:** `GET`

**URL:** `/api/messages/conversation/{conversationId}`

**Description:** Returns the messages for a conversation ordered by `createdAt` in ascending order.

**Path Variables:**
- `conversationId` — conversation ID

**Query Params:** None

**Request Body:** None

**Response Body:** `MessageDto[]`

**Example Response**

```json
[
  {
    "id": "6636f4b1a7f1b42c9e4d1a13",
    "conversationId": "6636f43fa7f1b42c9e4d1a11",
    "senderId": "6636f3c2a7f1b42c9e4d1a10",
    "content": "Hello world",
    "createdAt": "2026-05-07T15:10:45.456Z"
  },
  {
    "id": "6636f4eaa7f1b42c9e4d1a16",
    "conversationId": "6636f43fa7f1b42c9e4d1a11",
    "senderId": "6636f3e7a7f1b42c9e4d1a12",
    "content": "Hi Axel!",
    "createdAt": "2026-05-07T15:11:02.000Z"
  }
]
```

**Possible Status Codes**
- `200 OK`
- `500 Internal Server Error`

---

# Frontend Integration Examples

These examples are written for a React 19 frontend using the browser `fetch` API.

## Load all conversations for a user

```js
async function loadConversations(userId) {
  const response = await fetch(`http://localhost:8080/api/conversations/user/${userId}`);

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
}
```

## Load messages for an open conversation

```js
async function loadMessages(conversationId) {
  const response = await fetch(`http://localhost:8080/api/messages/conversation/${conversationId}`);

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
}
```

## Create a user

```js
async function createUser(user) {
  const response = await fetch("http://localhost:8080/api/users", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(user)
  });

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
}
```

## Send a message

```js
async function sendMessage(payload) {
  const response = await fetch("http://localhost:8080/api/messages", {
    method: "POST",
    headers: {
      "Content-Type": "application/json"
    },
    body: JSON.stringify(payload)
  });

  if (!response.ok) {
    throw new Error(await response.text());
  }

  return response.json();
}
```

## Example usage in React

```js
const handleSendMessage = async () => {
  try {
    const createdMessage = await sendMessage({
      senderId: currentUserId,
      recipientId: selectedUserId,
      content: inputValue
    });

    console.log("Message created:", createdMessage);
    // Refresh conversation preview and message list here
  } catch (error) {
    console.error("Failed to send message:", error.message);
  }
};
```

---

# Error Handling

The backend returns simple HTTP status codes with plain text error messages.

## 400 Bad Request

Returned when the request body is missing required information.

### Example response

```txt
participantIds must not be empty
```

### Another example

```txt
senderId and recipientId are required
```

## 404 Not Found

Returned when the requested resource does not exist.

### Example response

```txt
User not found with id: 6636f3c2a7f1b42c9e4d1a10
```

## 409 Conflict

Returned when a request tries to use an email that already exists.

### Example response

```txt
Email already exists: axel@example.com
```

## 500 Internal Server Error

Returned for unexpected server-side errors.

### Example response

```txt
Internal Server Error
```

### Frontend recommendation
Always check `response.ok` before parsing JSON. If the request fails, read the body as text.

---

# Suggested Frontend Flow

A simple and practical React flow is:

1. Load the current user's conversations
2. Open one conversation from the sidebar
3. Load messages for that conversation
4. Send a message with `POST /api/messages`
5. Refresh the conversation list and message list after sending

### Important behavior to know
- Sending a message can create the conversation automatically if it does not already exist
- Message history is returned in chronological order
- Empty arrays are valid responses when there is no data yet

---

# CORS Notes

The backend includes a global CORS configuration for `/api/**`.

## What this means for the frontend
Default allowed frontend origins are:

```txt
http://localhost:5173
http://localhost:3000
```

Requests from other origins will be blocked by the browser unless you update backend config.

The allowed origins are configured via:

```txt
app.cors.allowed-origins
```

in `src/main/resources/application.properties`.

## Current CORS behavior

- Applies to `/api/**`
- Allowed methods: `GET`, `POST`, `PUT`, `DELETE`, `OPTIONS`
- Allowed headers: `Content-Type`, `Accept`, `Authorization`
- Credentials: disabled (`allowCredentials(false)`)

## If your frontend uses another local origin
For example:

```txt
http://localhost:4173
```

add it to `app.cors.allowed-origins` and restart the backend.

## Development expectation
For local development, the frontend usually needs one of these setups:

- React app and API on the same origin
- A Vite or CRA proxy to `http://localhost:8080`
- A backend CORS policy that allows the React dev server origin

---

# Development Notes

- The API is JSON-only
- UTF-8 is expected for all request and response payloads
- Timestamps are returned as ISO-8601 values from Java `Instant`
- `null` values may appear in newly created conversations before the first message is sent
- Empty arrays are valid and should be handled gracefully in the UI
- There is no pagination yet
- There is no authentication yet

## Practical frontend tips
- Treat `ConversationDto.lastMessage` and `lastMessageAt` as preview fields for the sidebar
- After sending a message, refresh both the conversation list and the currently open message list
- Use stable React keys with backend IDs (`id`) for lists
- If a request fails, display the returned plain text message in a toast or alert

---

# Quick Reference

## Resource summary

- Users: `/api/users`
- Conversations: `/api/conversations`
- Messages: `/api/messages`

## Main objects

- `UserDto`
- `ConversationDto`
- `MessageDto`
- `CreateConversationRequest`
- `CreateMessageRequest`

## Status code summary

- `200 OK` — successful read/update
- `201 Created` — successful create/send
- `204 No Content` — successful delete
- `400 Bad Request` — invalid input
- `404 Not Found` — missing resource
- `409 Conflict` — duplicate email
- `500 Internal Server Error` — unexpected failure

