# URL Shortener API

## Introduction
A modern, efficient URL shortening service built with Spring Boot. This service allows you to convert long URLs into shorter, more manageable links while providing redirection capabilities.
Built in Java + Springboot for [learning purposes](https://roadmap.sh/projects/url-shortening-service)

## Table of Contents
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Getting Started](#getting-started)
    - [Prerequisites](#prerequisites)
    - [Installation](#installation)
- [API Documentation](#api-documentation)
- [Configuration](#configuration)
- [Contributing](#contributing)
- [License](#license)

## Features
- Shorten long URLs to compact codes
- Retrieve original URLs using short codes
- Input validation
- Error handling
- RESTful API design

## Tech Stack
- Java 17
- Spring Boot
- Spring Data MongoDB
- Gradle
- MongoDB
- Lombok

## Getting Started

### Prerequisites
- Java 17 or higher
- Gradle 8.0+
- MongoDB 4.4+

### Installation

1. Clone the repository
```bash
git clone https://github.com/glass-source/ShortBoot.git
```

2. Navigate to the project directory
```bash
cd ShortBoot
```

3. Build the project
```bash
./gradlew build
```

4. Run the application
```bash
./gradlew bootRun
```

For Windows users:
```bash
# Build
gradlew.bat build

# Run
gradlew.bat bootRun
```

### Running with IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Wait for the Gradle project to sync
3. Run the main application class (`UrlShortenerApplication`)

### Building a JAR
To build an executable JAR file:
```bash
./gradlew bootJar
```
The JAR will be created in `build/libs/` directory.

To run the JAR:
```bash
java -jar build/libs/urlShortener-0.0.1.jar
```

## API Documentation

### Endpoints

#### 1. Create Short URL
Convert a long URL into a shortened version.

- **Endpoint**: `POST /api/shorten`
- **Content-Type**: `application/json`
- **Request Body**:
```json
{
    "url": "https://www.example.com/very/long/url/that/needs/shortening"
}
```
- **Response**:
    - Status: 201 Created
```json
{
  "id": "67bf769d18f21a3005d9f2f4",
  "url": "https://originalUrl.com/",
  "shortCode": "8KX4ubfK",
  "accessCount": 0,
  "createdAt": "2025-02-26T16:16:29Z",
  "updatedAt": "2025-02-26T16:16:29Z"
}
```
- **Error Response**:
    - Status: 400 Bad Request
```json
{
  "url": "Invalid URL format"
}
```

#### 2. Get Original URL
Retrieve the original URL using a short code.

- **Endpoint**: `GET /api/shorten/{shortCode}`
- **Parameters**:
    - `shortCode`: The shortened code for the URL
- **Response**:
    - Status: 200 OK
```json
{
    "url": "https://www.example.com/very/long/url/that/needs/shortening"
}
```
- **Error Response**:
    - Status: 404 Not Found
```json
{
    "error": "Short URL not found"
}
```

#### 3. Update URL
Update an existing shortened URL mapping.

- **Endpoint**: `PUT /api/shorten/{shortCode}`
- **Content-Type**: `application/json`
- **Parameters**:
  - `shortCode`: The code of the shortened URL to update
- **Request Body**:
```json
{
    "url": "https://newexample.com/updated/url"
}
```
- **Response**:
  - Status: 200 OK
```json
{
  "id": "67bf782c18f21a3005d9f2f5",
  "url": "https://newexample.com/updated/url",
  "shortCode": "shortCode",
  "accessCount": 0,
  "createdAt": "2025-02-26T16:23:08Z",
  "updatedAt": "2025-02-26T16:28:30Z"
}
```
- **Error Responses**:
  - Status: 404 Not Found
```json
{
    "error": "Short URL not found"
}
```
- Status: 400 Bad Request
```json
{
    "error": "Invalid URL format"
}
```

#### 4. Delete Short URL
Remove a shortened URL mapping from the system.

- **Endpoint**: `DELETE /api/shorten/{shortCode}`
- **Parameters**:
  - `shortCode`: The code of the shortened URL to delete
- **Response**:
  - Status: 204 No Content
- **Error Response**:
  - Status: 404 Not Found
```json
{
    "error": "Short URL not found"
}
```

## Configuration

### Application Properties
```properties
spring.application.name=urlShortener
spring.data.mongodb.host=localhost
spring.data.mongodb.port=27017
spring.data.mongodb.database=urlshortener
spring.data.mongodb.auto-index-creation=true
```

## Contributing
1. Fork the project
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License
This project is licensed under the MIT License - see the [LICENSE.txt](LICENSE.txt) file for details.