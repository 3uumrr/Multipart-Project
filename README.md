# Multipart Test - Spring Boot File Upload Application

A Spring Boot REST API application demonstrating multipart file upload functionality for managing products and their associated images.

## Features

- ✅ **Product Management**: Create, read, and search products
- ✅ **Image Upload**: Upload multiple images per product
- ✅ **Image Management**: Get, update, and delete images
- ✅ **RESTful API**: Clean REST endpoints with proper HTTP methods
- ✅ **Database Integration**: MySQL database with JPA/Hibernate
- ✅ **API Documentation**: OpenAPI/Swagger integration
- ✅ **Exception Handling**: Global exception handler for error management
- ✅ **DTO Mapping**: MapStruct for efficient object mapping

## Tech Stack

- **Java 17**
- **Spring Boot 3.3.5**
- **Spring Data JPA**
- **MySQL Database**
- **Lombok** - Reduces boilerplate code
- **MapStruct** - Type-safe bean mapping
- **SpringDoc OpenAPI** - API documentation
- **Maven** - Dependency management

## Prerequisites

Before running this application, ensure you have:

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+ installed and running
- A MySQL database named `multipart_test` created

## Installation & Setup

### 1. Clone the Repository

```bash
git clone <repository-url>
cd multipartTest
```

### 2. Database Configuration

Create a MySQL database:

```sql
CREATE DATABASE multipart_test;
```

Update the database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/multipart_test
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/multipartTest-0.0.1-SNAPSHOT.jar
```

The application will start on `http://localhost:9091`

## API Endpoints

### Product Endpoints

#### Get All Products
```
GET /products
```

**Response:**
```json
{
  "message": "Success",
  "data": [
    {
      "id": 1,
      "name": "Product Name",
      "price": 99.99,
      "quantity": 10,
      "images": [...]
    }
  ]
}
```

#### Get Product by ID
```
GET /products/{id}
```

#### Create Product with Images
```
POST /products
Content-Type: multipart/form-data
```

**Request:**
- `product` (JSON): Product details
  ```json
  {
    "name": "Product Name",
    "price": 99.99,
    "quantity": 10
  }
  ```
- `images` (Files): One or more image files (optional)

**Example using cURL:**
```bash
curl -X POST http://localhost:9091/products \
  -F "product={\"name\":\"Test Product\",\"price\":99.99,\"quantity\":10}" \
  -F "images=@image1.jpg" \
  -F "images=@image2.jpg"
```

#### Search Products
```
POST /products/search
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "Product Name",
  "price": "99.99"
}
```

### Image Endpoints

#### Get Image by ID
```
GET /images/{id}
```

#### Update Image
```
PUT /images/{id}
Content-Type: multipart/form-data
```

**Request:**
- `file` (File): New image file

#### Delete Image
```
DELETE /images/{id}
```

## API Documentation

Once the application is running, access the Swagger UI at:

```
http://localhost:9091/swagger-ui.html
```

Or the OpenAPI JSON at:

```
http://localhost:9091/v3/api-docs
```

## Project Structure

```
src/
├── main/
│   ├── java/com/example/multipartTest/
│   │   ├── config/          # Configuration classes
│   │   ├── controller/      # REST controllers
│   │   ├── exceptions/      # Exception handling
│   │   ├── mapper/          # MapStruct mappers
│   │   ├── models/          # JPA entities
│   │   ├── repository/      # JPA repositories
│   │   ├── request/         # Request DTOs
│   │   ├── response/        # Response DTOs
│   │   ├── services/        # Business logic
│   │   └── specification/   # JPA specifications
│   └── resources/
│       └── application.properties
└── uploads/                 # Uploaded files directory
```

## Configuration

Key configuration properties in `application.properties`:

- **Server Port**: 9091
- **Database**: MySQL (multipart_test)
- **JPA**: Hibernate with auto-update DDL
- **File Upload**: Files are stored in the `uploads/` directory

## Response Format

All API responses follow a consistent format:

```json
{
  "message": "Success message",
  "data": { ... }
}
```

## Error Handling

The application includes a global exception handler that returns standardized error responses:

```json
{
  "message": "Error message",
  "data": null
}
```

## Development

### Building for Production

```bash
mvn clean package
```

This will create a JAR file in the `target/` directory.

### Running Tests

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is open source and available under the MIT License.

## Author

Created as a demonstration project for multipart file upload functionality in Spring Boot.

---

For more information about Spring Boot, visit [spring.io](https://spring.io/projects/spring-boot)

