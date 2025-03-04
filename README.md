# Ledger Service API

## Overview
A simple RESTful ledger service built with Spring Boot that allows you to track financial transactions. This application maintains a ledger of deposits and withdrawals, calculates the current balance, and provides transaction history.

## Features
- Create deposit and withdrawal transactions
- Check current balance
- View paginated transaction history
- Validation of transaction data
- Exception handling for insufficient funds and validation errors

## Prerequisites
- Java 17
- Maven 3.6
- Spring Boot 3.x

## Installation

1. Clone this repository
```bash
   git clone https://github.com/fahad1988/ledger.git
   cd ledger
```
2. Build the application with Maven
```bash
mvn clean install
```

## Running the Application

Start the application using Maven or as a JAR file. The application will start on `http://localhost:8080` by default.
```bash
mvn spring-boot:run
```

## API Endpoints

### 1. Create a Transaction

**Endpoint:** `POST /api/v1/ledger/transaction`

**Request Body:**
```json
{
  "amount": 100.00,
  "description": "Initial deposit",
  "transactionType": "DEPOSIT"
}
```

**Response:**
```json
{
  "id": 1,
  "timestamp": "2025-03-01T12:34:56.789",
  "amount": 100.00,
  "description": "Initial deposit",
  "transactionType": "DEPOSIT"
}
```

### 2. Get Current Balance

**Endpoint:** `GET /api/v1/ledger/balance`

**Response:**
```json
{
  "balance": 100.00
}
```

### 3. Get Transaction History

**Endpoint:** `GET /api/v1/ledger/transactions?page=0&size=10`

**Parameters:**
- `page`: Page number (zero-based, default is 0)
- `size`: Page size (default is 10)

**Response:**
```json
{
  "content": [
    {
      "id": 1,
      "timestamp": "2025-03-04T12:34:56.789",
      "amount": 100.00,
      "description": "Initial deposit",
      "transactionType": "DEPOSIT"
    },
    {
      "id": 2,
      "timestamp": "2025-03-02T12:36:42.123",
      "amount": 50.00,
      "description": "Withdrawal for groceries",
      "transactionType": "WITHDRAWAL"
    }
  ],
  "pageable": {
    "sort": {
      "empty": true,
      "sorted": false,
      "unsorted": true
    },
    "offset": 0,
    "pageNumber": 0,
    "pageSize": 10,
    "paged": true,
    "unpaged": false
  },
  "last": true,
  "totalPages": 1,
  "totalElements": 2,
  "size": 10,
  "number": 0,
  "sort": {
    "empty": true,
    "sorted": false,
    "unsorted": true
  },
  "first": true,
  "numberOfElements": 2,
  "empty": false
}
```

## Error Response

### Testing error handling - insufficient funds

**Endpoint:** `POST /api/v1/ledger/transaction`

**Request:**
```json
{
  "amount": 2000.00,
  "description": "Withdrawal attempt",
  "transactionType": "WITHDRAWAL"
}
```

**Error Response:**
```json
{
  "Errors": {
    "errorMessage": ["Insufficient Funds"]
  }
}
```

## Error Handling

The API returns appropriate HTTP status codes along with error messages:

- `400 Bad Request`: For validation errors or insufficient funds
- `405 Method Not Allowed`: For unsupported HTTP methods

Example validation error response:

```json
{
  "Errors": {
    "errorMessage": ["amount : Amount must be positive"]
  }
}
```

## Implementation Details

- In-memory transaction storage using `ArrayList`
- Thread-safe balance management with atomic operations and `ReadWriteLock`
- Pagination support for transaction history
- Bean validation for transaction requests

## Technical Stack

- Java
- Spring Boot
- Bean Validation API

## Project Structure

- `controller`: REST API endpoints
- `service`: Business logic
- `repository`: Data access layer
- `model`: Domain models
- `dto`: Data transfer objects
- `exception`: Custom exceptions and error handling
