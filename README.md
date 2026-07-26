# Discipline Backend

A Spring Boot REST API backend for tracking discipline/habits.

## Tech Stack
- Java 17
- Spring Boot 3.5.11
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Prerequisites
- JDK 17+
- Maven (or use the included `mvnw` wrapper)
- MySQL running locally (or update `application.properties` for your DB)

## Getting Started

### Clone the repository
```bash
git clone https://github.com/Santhoshreddy997/discipline-backend.git
cd discipline-backend
```

### Configure the database
Update `src/main/resources/application.properties` (or `.yml`) with your MySQL credentials:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/discipline_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### Run the application
```bash
./mvnw spring-boot:run
```
The API will start on `http://localhost:8080` by default.

### Run with Docker
```bash
docker build -t discipline-backend .
docker run -p 8080:8080 discipline-backend
```

## Project Structure
