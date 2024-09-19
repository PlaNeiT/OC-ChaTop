# ChâTop Backend Project

ChâTop is a backend service for a property rental application designed to connect potential tenants with property owners. This service provides authentication, rental management, and messaging between users.

## Features

- User Authentication (JWT)
- CRUD operations for Rentals and Messages
- Integration with AWS S3 for image storage
- API documentation with Swagger

## Prerequisites

Ensure you have the following installed on your machine:

- **Java 17** or higher
- **Maven** (for building the backend)
- **MySQL** (for database setup)
- **Node.js** and **npm** (for frontend setup if needed)
- **Angular CLI** (for running the Angular frontend)
- **AWS account** for S3 bucket access

## Database Setup

To initialize the MySQL database, run the following SQL script:

```sql
DROP DATABASE IF EXISTS `chatop`;
CREATE DATABASE `chatop`;
USE `chatop`;

-- Table USERS
DROP TABLE IF EXISTS `USERS`;
CREATE TABLE IF NOT EXISTS `USERS` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `email` varchar(255) NOT NULL,
  `name` varchar(255),
  `password` varchar(255),
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Table RENTALS
DROP TABLE IF EXISTS `RENTALS`;
CREATE TABLE IF NOT EXISTS `RENTALS` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `name` varchar(255),
  `surface` DECIMAL(10, 2),
  `price` DECIMAL(10, 2),
  `picture` varchar(255),
  `description` varchar(2000),
  `owner_id` BIGINT NOT NULL,
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `fk_owner` FOREIGN KEY (`owner_id`) REFERENCES `USERS` (`id`) ON DELETE CASCADE
);

-- Table MESSAGES
DROP TABLE IF EXISTS `MESSAGES`;
CREATE TABLE IF NOT EXISTS `MESSAGES` (
  `id` BIGINT PRIMARY KEY AUTO_INCREMENT,
  `rental_id` BIGINT,
  `user_id` BIGINT,
  `message` varchar(2000),
  `created_at` timestamp DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  CONSTRAINT `fk_rental` FOREIGN KEY (`rental_id`) REFERENCES `RENTALS` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `USERS` (`id`) ON DELETE CASCADE
);

-- Unique index on email in USERS table
CREATE UNIQUE INDEX `USERS_index` ON `USERS` (`email`);
```
## Configuration

### Application Properties

Configure the following properties in `application.properties`:

```properties
# ==============================================
# MySQL Database Configuration
# ==============================================
# Use environment variables for credentials
spring.datasource.url=jdbc:mysql://localhost:3306/chatop
# TODO: Change to your MySQL username
spring.datasource.username=root
# TODO: Change to your MySQL password
spring.datasource.password=root

# ==============================================
# AWS S3 Configuration
# ==============================================
# TODO: Change to your AWS Access Key
cloud.aws.credentials.access-key=${AWS_ACCESS_KEY}
# TODO: Change to your AWS Secret Key
cloud.aws.credentials.secret-key=${AWS_SECRET_KEY}
# TODO: Change to your AWS S3 bucket region
cloud.aws.region.static=eu-west-3
# TODO: Change to your AWS S3 bucket name
cloud.aws.s3.bucket=p3-oc-chatop-bucket

# ==============================================
# JWT (JSON Web Token) Configuration
# ==============================================
# Secret key used to sign JWTs
jwt.secret=f8a9a1c3b529f77dbf561c6b12a10c8e18f2f2a77d08d74e634f20d27a7be8b5
# JWT token expiration time in milliseconds (24 hours)
jwt.expiration=86400000

```
## AWS S3 Setup

Ensure your AWS credentials are set correctly and you have access to the S3 bucket specified in `application.properties`. If the bucket does not exist, create one in your AWS account.

## API Documentation

Once the application is running, you can access the API documentation at:

[http://localhost:3001/swagger-ui/index.html](http://localhost:3001/swagger-ui/index.html)

## Running the Project

### Frontend Setup

If you want to run the Angular frontend:

```bash
# Navigate to the frontend directory (if applicable)
cd path/to/frontend

# Install dependencies
npm install

# Start the frontend
npm run start
```


### Backend Setup
To run the backend locally:

```bash
# Build the backend
mvn clean install

# Run the backend
mvn spring-boot:run
```
Alternatively, you can start the backend using the main class:

```bash
mvn exec:java -D exec.mainClass="com.openclassrooms.OC_ChaTop.OcChaTopApplication"
```
## Testing the Application

Use the provided Postman collection to test the API endpoints. Import the collection and set the appropriate environment variables to interact with the backend. You can find the Postman collection and Mockoon environment setup in:

- `/resources/postman`
- `/resources/mockoon`

## Troubleshooting

- **Database Connection Issues**: Check the `application.properties` file to ensure the correct MySQL credentials are provided.
- **AWS S3 Issues**: Ensure that your AWS credentials are correct and the bucket exists in the specified region.
- **JWT Expiration**: Make sure the JWT secret is secure and correctly configured. To generate a secure JWT secret easily, you can use the following command:

  ```bash
  openssl rand -base64 32
```
