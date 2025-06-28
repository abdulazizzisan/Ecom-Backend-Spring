# E-Commerce Backend - Spring Boot REST API

A robust, enterprise-grade e-commerce backend application built with Spring Boot, featuring JWT authentication, PostgreSQL database integration, and comprehensive API documentation.

## 🚀 Quick Start

### Prerequisites
- Java 21+
- Docker & Docker Compose
- Maven 3.6+
- PostgreSQL (or use Docker Compose)

### Running the Application

1. **Clone the repository**
   ```bash
   git clone https://github.com/abdulazizzisan/Ecom-Backend-Spring.git
   cd Ecom-Backend-Spring
   ```

2. **Set up environment variables**
   Create a `.env` file in the root directory:
   ```properties
   DB_PASS=your_database_password
   ```

3. **Start the database**
   ```bash
   docker-compose up -d
   ```

4. **Run the application**
   ```bash
   mvn spring-boot:run
   ```

5. **Access the application**
   - API Documentation (Swagger UI): http://localhost:8080/
   - API Docs JSON: http://localhost:8080/api-docs

## 🛠️ Technology Stack

### Core Framework & Language
- **Java 21** - Latest LTS version with modern language features
- **Spring Boot 3.4.4** - Latest enterprise framework with auto-configuration
- **Maven** - Dependency management and build automation

### Database & ORM
- **PostgreSQL** - Production-grade relational database
- **Spring Data JPA** - Data access layer with repository pattern
- **Hibernate** - Advanced ORM with automatic DDL generation
- **Docker Compose** - Containerized database setup for development

### Security Implementation
- **Spring Security 6** - Comprehensive security framework
- **JWT (JSON Web Tokens)** - Stateless authentication mechanism
- **BCrypt Password Encoding** - Industry-standard password hashing
- **Method-level Security** - Fine-grained access control with `@PreAuthorize`
- **Role-based Access Control (RBAC)** - User and Admin role segregation

### API & Documentation
- **RESTful API Design** - Following REST architectural principles
- **OpenAPI 3 (Swagger)** - Interactive API documentation
- **SpringDoc OpenAPI** - Auto-generated API specs from annotations

### Development & DevOps
- **Spring Boot DevTools** - Hot reload and development utilities
- **Spring Boot Actuator** - Production monitoring and management endpoints
- **Lombok** - Boilerplate code reduction with annotations
- **Docker Compose** - Containerized development environment

## 🏗️ Architecture & Design Patterns

### Layered Architecture
```
├── Controller Layer (REST endpoints)
├── Service Layer (Business logic)
├── Repository Layer (Data access)
└── Model Layer (Entities & DTOs)
```

### Key Design Patterns Implemented
- **Repository Pattern** - Data access abstraction
- **DTO Pattern** - Data transfer objects for API contracts
- **Builder Pattern** - Object construction (via Lombok)
- **Dependency Injection** - Loose coupling with Spring IoC
- **Filter Chain Pattern** - JWT authentication filter

### Security Architecture
- **Stateless Authentication** - JWT-based session management
- **Custom Authentication Filter** - `JwtAuthFilter` for token validation
- **User Details Service** - Custom user loading mechanism
- **Password Encryption** - BCrypt with salt for secure password storage

## 🔒 Security Features

### Authentication & Authorization
```java
// JWT-based stateless authentication
@Component
public class JwtAuthFilter extends OncePerRequestFilter

// Role-based method level security
@PreAuthorize("hasAuthority('ADMIN')")
public CategoryResponse createCategory(@RequestBody CategoryRequest request)
```

### Security Configuration
- CSRF protection disabled for stateless API
- Session management set to STATELESS
- JWT token validation with expiration handling
- Secure password encoding with BCrypt
- Public endpoints for authentication and documentation

### User Management
- User registration with role assignment
- Secure login with JWT token generation
- User details integration with Spring Security
- Phone number and email validation

## 📊 Database Design

### Entity Relationships
- **User Entity** - Authentication and user management
  - OneToOne with Address
  - OneToMany with Reviews
- **Category Entity** - Product categorization
  - OneToMany with Products
  - Timestamp tracking (CreatedAt, UpdatedAt)
- **Audit Fields** - Automatic timestamp management with Hibernate annotations

### Database Features
- **Automatic Schema Generation** - Hibernate DDL auto-update
- **Connection Pooling** - Efficient database connection management
- **SQL Logging** - Development debugging with formatted SQL output
- **PostgreSQL Dialect** - Database-specific optimizations

## 🌐 API Endpoints

### Authentication Endpoints
```
POST /api/v1/auth/user/register - User registration
POST /api/v1/auth/user/login    - User authentication
```

### Category Management (Admin Only)
```
POST   /api/v1/categories     - Create category
GET    /api/v1/categories     - List all categories
GET    /api/v1/categories/{id} - Get category by ID
PUT    /api/v1/categories/{id} - Update category
DELETE /api/v1/categories/{id} - Delete category
GET    /api/v1/categories/{id}/products - Get category products
```

## 📚 API Documentation

### Swagger Integration
- **Interactive API Testing** - Built-in Swagger UI
- **Comprehensive Documentation** - Detailed endpoint descriptions
- **Request/Response Examples** - Auto-generated from annotations
- **Authentication Testing** - JWT token integration in Swagger

### OpenAPI Annotations
```java
@Tag(name = "Category", description = "Category API")
@Operation(summary = "Create a new category", description = "Create a new category with the given details")
```

## 🔧 Configuration Management

### External Configuration
- **Environment-based Configuration** - `.env` file support
- **Profile-based Settings** - Different configurations for dev/prod
- **Externalized Properties** - Database credentials and API settings

### Application Properties
```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5433/ecomdb
spring.jpa.hibernate.ddl-auto=update

# API Documentation
springdoc.swagger-ui.path=/
springdoc.api-docs.path=/api-docs
```

## 🧪 Development Features

### Hot Reload & Development Tools
- **Spring Boot DevTools** - Automatic application restart
- **Live Reload** - Browser refresh on changes
- **Property Defaults** - Development-optimized settings

### Code Quality
- **Lombok Integration** - Reduced boilerplate code
- **Annotation Processing** - Compile-time code generation
- **Clean Code Practices** - Separation of concerns and SOLID principles

## 🐳 Docker Integration

### Development Environment
```yaml
# PostgreSQL containerized setup
services:
  postgres:
    image: 'postgres:latest'
    ports: ['5433:5432']
    volumes: ['postgres_data:/var/lib/postgresql/data']
```

### Database Management
- **Volume Persistence** - Data survival across container restarts
- **Easy Reset** - Volume removal for fresh database state
- **Backup/Restore** - Docker-based database operations

## 🚀 Production Readiness

### Monitoring & Health Checks
- **Spring Boot Actuator** - Health endpoints and metrics
- **Application Monitoring** - Built-in health indicators
- **Custom Health Checks** - Database connectivity validation

### Performance Optimizations
- **Connection Pooling** - Efficient database connections
- **JPA Query Optimization** - Hibernate performance tuning
- **Stateless Design** - Horizontal scalability support

## 🏆 Key Technical Achievements

1. **Modern Java Features** - Leveraging Java 21 capabilities
2. **Enterprise Security** - Production-grade JWT implementation
3. **Clean Architecture** - Maintainable and testable code structure
4. **API-First Design** - Comprehensive OpenAPI documentation
5. **Containerized Development** - Docker-based development environment
6. **Type Safety** - Strong typing with DTOs and validation
7. **Audit Trail** - Automatic timestamp tracking for entities
8. **Role-based Security** - Fine-grained access control

This project demonstrates proficiency in modern Spring Boot development, security best practices, API design, database management, and DevOps practices suitable for enterprise-level applications.
