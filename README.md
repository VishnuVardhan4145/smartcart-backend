# SmartCart Backend

SmartCart Backend is a secure and scalable E-Commerce REST API built using Spring Boot, Spring Security, JWT Authentication, MySQL, and JPA/Hibernate.

## Features

* JWT Authentication
* Refresh Token Authentication
* Role-Based Authorization
* Product Management APIs
* Cart Management APIs
* Order Management APIs
* Pagination & Sorting
* Search APIs
* Swagger API Documentation
* Global Exception Handling
* Integration Testing
* JaCoCo Code Coverage

## Tech Stack

* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Lombok
* JWT
* Swagger OpenAPI
* JUnit & MockMvc

## Project Structure

```text
src/main/java/com/smartcart
│
├── controller
├── service
├── repository
├── entity
├── dto
├── security
├── exception
└── config
```

## Installation & Setup

### Clone Repository

```bash
git clone https://github.com/your-username/smartcart-backend.git
cd smartcart-backend
```

### Configure Database

Update `application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/smartcart
spring.datasource.username=root
spring.datasource.password=yourpassword
```

### Run Application

```bash
mvn spring-boot:run
```

## Swagger API Documentation

Open:

```text
http://localhost:8080/swagger-ui/index.html
```

## Run Tests

```bash
mvn test
```

## Generate Code Coverage Report

```bash
mvn test
```

JaCoCo report:

```text
target/site/jacoco/index.html
```

## Author

Vishnu Vardhan
