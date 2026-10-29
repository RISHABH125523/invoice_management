# Invoice Management System

A RESTful Invoice Management API built using **Java, Spring Boot, Spring Data JPA, and MySQL**. The application manages invoices, validates input data, calculates CGST and SGST dynamically, and stores invoice details in a database.

## Features

- Create a new invoice
- Retrieve an invoice by ID
- Retrieve all invoices
- Update an existing invoice
- Delete an invoice
- Automatically calculate taxable amount, CGST, SGST, and total amount
- Validate invoice input data
- Handle errors using custom and global exception handling
- Persist invoice data using Spring Data JPA
- Manage database transactions

## Technology Stack

- **Java** — Backend programming language
- **Spring Boot** — Application framework
- **Spring Web** — REST API development
- **Spring Data JPA** — Database persistence
- **Hibernate** — ORM implementation
- **MySQL** — Relational database
- **Bean Validation** — Input validation
- **Git and GitHub** — Version control

> Include only the technologies and features actually configured in your project.

## Project Structure

```text
invoice/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/invoice/
│   │   │       ├── InvoiceManagementApplication.java
│   │   │       ├── controller/
│   │   │       │   └── InvoiceController.java
│   │   │       ├── service/
│   │   │       │   └── InvoiceService.java
│   │   │       ├── repository/
│   │   │       ├── entity/
│   │   │       ├── dto/
│   │   │       └── exception/
│   │   └── resources/
│   │       └── application.properties
│   └── test/
├── pom.xml
├── README.md
└── database/
    └── schema.sql
```

*Adjust the structure to match your actual project files.*

## Prerequisites

Install the following software:

- Java JDK compatible with your Spring Boot version
- Maven or the included Maven Wrapper
- PostgreSQL or MySQL, depending on your configuration
- Git (optional, for cloning the repository)

Verify Java and Maven:

```bash
java -version
mvn -version
```

## Installation and Setup

### 1. Clone the repository

```bash
git clone YOUR_GITHUB_REPOSITORY_URL
cd invoice
```

### 2. Create the database

Create a database using your configured database system.

Example for MYSQL:

```mysql
CREATE DATABASE invoice_management;
```

Use the database name configured in your application.

### 3. Configure database properties

Open `src/main/resources/application.properties` and configure your database connection.

Example for MYSQL:

```properties
spring.datasource.url=jdbc:mysql://localhost:5432/invoice_db
spring.datasource.username=${DB_USERNAME}
spring.datasource.password=${DB_PASSWORD}

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Ensure the required database driver is included in `pom.xml`. Set credentials using environment variables or another secure configuration method. Do not commit real passwords to GitHub.

If you use MySQL, configure the MySQL JDBC URL and driver instead.

### 4. Run the application

Using Maven:

```bash
mvn spring-boot:run
```

On Windows, if the Maven Wrapper is included:

```bash
mvnw.cmd spring-boot:run
```

### 5. Build the application

```bash
mvn clean package
```

Run the generated JAR using its actual filename:

```bash
java -jar target/YOUR_APPLICATION.jar
```

The application typically starts at:

```text
http://localhost:8080
```

## REST API Endpoints

The following is an example API design. Update the routes to match your `InvoiceController`.

| HTTP Method | Endpoint             | Description               |
| ----------- | -------------------- | ------------------------- |
| POST        | `/api/invoices`      | Create an invoice         |
| GET         | `/api/invoices`      | Retrieve all invoices     |
| GET         | `/api/invoices/{id}` | Retrieve an invoice by ID |
| PUT         | `/api/invoices/{id}` | Update an invoice         |
| DELETE      | `/api/invoices/{id}` | Delete an invoice         |

Use Postman, Swagger UI (if configured), or another API client to test the endpoints.

## GST Calculation

The application calculates GST based on the taxable amount.

**Example:**

| Item             |      Amount |
| ---------------- | ----------: |
| Quantity         |           2 |
| Unit Price       |     ₹100.00 |
| Taxable Amount   |     ₹200.00 |
| CGST (9%)        |      ₹18.00 |
| SGST (9%)        |      ₹18.00 |
| **Total Amount** | **₹236.00** |

Formulas:

- Taxable Amount = Quantity × Unit Price
- CGST = Taxable Amount × CGST Rate / 100
- SGST = Taxable Amount × SGST Rate / 100
- Total Amount = Taxable Amount + CGST + SGST

This example assumes an 18% total GST rate split equally between CGST and SGST. The actual tax rate should be configurable or determined according to the applicable business rules.

## Validation and Exception Handling

The application should validate invoice fields to prevent invalid data, such as missing descriptions, non-positive quantities, or invalid prices.

A global exception handler can return consistent JSON responses for validation errors, missing invoices, and unexpected server errors.

Example error response:

```json
{
  "message": "Invoice not found",
  "status": 404
}
```

*The response format depends on your actual exception-handler implementation.*


## Future Enhancements

- Swagger/OpenAPI documentation
- Unit and integration tests
- Docker containerization
- Improved logging and monitoring
