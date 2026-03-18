# Test-Automation-API-Project

## Description

This project contains automated API tests for the Swagger Petstore using **Java, Maven, RestAssured, and TestNG**.

The objective of this project is to validate key functionalities of the Petstore API by implementing automated tests following good practices such as:

- Separation of concerns (config, models, requests, tests)
- Reusable request builder
- DTO-based response handling
- Independent and deterministic tests

---

## Technologies Used

- Java 8+
- Maven
- RestAssured
- TestNG
- Lombok

---

## Tested Endpoints

The following functionalities were automated:

### User
- Create User → `POST /user`
- Login → `GET /user/login`
- Logout → `GET /user/logout`

### Pet
- Find pets by status → `GET /pet/findByStatus`
- Get pet by ID → `GET /pet/{petId}`

### Store
- Create order → `POST /store/order`

---

## Project Structure
src/test/java/com/globant/automation
│
├── config → Environment configuration (TestRunner)
├── model → DTOs (User, Pet, Order, ApiResponse)
├── request → RequestBuilder (HTTP logic)
└── test → Test classes per functionality

---

## Configuration

The environment configuration is managed through:

src/test/resources/config.properties

Example:

url.base=https://petstore.swagger.io/v2
apikey=special-key

---

## How to Run Tests

Run all tests using Maven:

```bash
mvn clean test

---
