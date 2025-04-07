# QR Code Service

This project is a Spring Boot application that provides a service for processing QR code images and extracting data from them.
The application uses Spring Security for authentication and authorization, and JWT tokens for securing the endpoints.

There is an endpoint which allows generation of a single QR code image as well. Refer to the "Available REST API Endpoints" section for more details.

## Steps to Run the Application

1. **Set Up PostgreSQL Database**:
    - Create a PostgreSQL database named `qr_code_service`.
    - Update the `src/main/resources/application.properties` file with your PostgreSQL database credentials:
        ```properties
        spring.datasource.url=jdbc:postgresql://localhost:5432/qr_code_service
        spring.datasource.username=your_username
        spring.datasource.password=your_password
        spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
        spring.jpa.hibernate.ddl-auto=create
        ```
    - For more information on setting up PostgreSQL, refer to the [PostgreSQL Documentation](https://www.postgresql.org/docs/current/index.html).

2. **Switch to H2 Database (Optional)**:
   If you are unable to set up a PostgreSQL database, you can switch to H2 Database by commenting/uncommenting the necessary lines in the `src/main/resources/application.properties` file:
    ```properties
    #spring.h2.console.enabled=true
    #spring.h2.console.path=/h2
    #spring.datasource.url=jdbc:h2:mem:qr_code_service
    #spring.jpa.hibernate.ddl-auto=create
    
    # Comment out these lines for PostgreSQL
    #spring.datasource.url=jdbc:postgresql://localhost:5432/qr_code_service
    #spring.datasource.username=your_username
    #spring.datasource.password=your_password
    #spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
    ```

3. **Build and Run the Application**:
    - This application uses Maven as its build tool. Make sure you have Maven installed on your machine, or use the provided `mvnw` (Maven Wrapper) in the project.
    ```sh
    ./mvnw spring-boot:run
    ```

## Available REST API Endpoints

### Authentication
- **POST /auth/login**: Provide the correct credentials to login and receive the JWT Token.

### QR Code Data
- **GET /qr-code/data/{id}**: Retrieve the required QR code information for the given ID.
- **GET /qr-code/data/all**: Retrieve all the QR code information.
- **DELETE /qr-code/data/{id}**: Delete the QR code information for the given ID. (Requires JWT token in the HTTP "Authorization" request header)
- **DELETE /qr-code/data/all**: Delete all the QR code information. (Requires JWT token in the HTTP "Authorization" request header)

### QR Code Image
- **GET /qr-code/image/{id}**: Retrieve the required QR code image for the given ID.

### QR Code Upload
- **POST /qr-code/upload**: Upload a QR code image, process, and save its data. (Requires JWT token in the HTTP "Authorization" request header)
- **POST /qr-code/upload/multiple**: Upload multiple QR code images, process, and save their data. (Requires JWT token in the HTTP "Authorization" request header)

## Static QR Code Images

Static QR code images can be found in the `src/main/resources/static` folder.

## Mock Data

Mock data will be inserted into the database upon starting the application. The `DataLoader` class located in the `src/main/java/challenge/tech/crud_auth/qr_code_service/entity` folder is responsible for inserting mock data into the database.

---

Feel free to reach out if you have any questions or need further assistance.