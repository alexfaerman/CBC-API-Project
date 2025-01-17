### CBC API Project

## Instructions to Build, Test, and Run the Backend and UI applications

### Prerequisites
- Java 21 or higher
- Maven 3.8.1 or higher
- Docker (For PostgreSQL setup)
- Flyway 11.1 or higher (for managing database migrations)
- Postman or curl: For testing API endpoints
- Node.js 18 or higher (https://nodejs.org/en)
- npm 8.x or higher (comes with Node.js installation)

### Steps to Build the Backend application
1. Clone the repository:
   ```cmd
   git clone <repository-url>
   cd <repository-folder>
   cd cbc-api
   ```
2. Build the application using Maven:
   ```cmd
   mvn clean install
   ```

### Steps to Run the Application
1. Start a PostgreSQL instance using Docker:
   ```cmd
   docker run --name postgres-cbc -e POSTGRES_USER=cbcuser -e POSTGRES_PASSWORD=cbcpassword -e POSTGRES_DB=cbcdb -p 5432:5432 -d postgres
   ```
2. Verify the database configuration in `application.properties` and `application-test.properties`:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/cbcdb
   spring.datasource.username=cbcuser
   spring.datasource.password=cbcpassword
   spring.jpa.hibernate.ddl-auto=update
   ```
3. Run the Spring Boot application:
   ```cmd
   mvn spring-boot:run
   ```

---

### Steps to Test the Application

1. **Run Unit Tests:**
   ```cmd
   mvn test -Ptest
   ```

2. **Use Postman or Curl to Test the REST API Endpoints:**

   - **Single Item POST Request:**
     ```cmd
     curl -X POST http://localhost:8080/items?ContentSource=A -H "Content-Type: application/json" -d "{\"itemId\": \"A12345\", \"title\": \"Article A\", \"author\": \"John Doe\", \"publishedYear\": 2023, \"typeA\": \"article\"}"
     ```

   - **GET Request (UUID is the `id` returned from the previous POST request):**
     ```cmd
     curl http://localhost:8080/items/<UUID>
     ```

   - **Bulk POST Request:**
     ```cmd
     curl -X POST http://localhost:8080/items/bulk -H "Content-Type: application/json" -d "{\"ContentSourceA\": {\"itemId\": \"A12345\", \"title\": \"Article A\", \"author\": \"John Doe\", \"publishedYear\": 2023, \"typeA\": \"article\"}, \"ContentSourceB\": {\"itemNumber\": \"B67890\", \"itemTitle\": \"Story B\", \"authorName\": \"Jane Smith\", \"yearPublished\": 2022, \"typeB\": \"story\"}, \"ContentSourceC\": {\"item_id\": \"C54321\", \"name_of_work\": \"Video C\", \"published_by\": \"Sam Johnson\", \"year_of_publication\": 2024, \"typeC\": \"video\"}}"
       ```

---

### Steps to Run the UI Project

1. **Navigate to the Project Directory**:  
   Change your directory to the UI project folder:
   ```cmd
   cd /<repository-folder>/cbc-ui

2. **Install Dependencies**:  
   ```cmd
   npm install

3. **Start the UI Development Server**:  
   ```cmd
   npm start

---
**This will launch the application in your default web browser at http://localhost:3000.**


---


## Application Design Details

### Overview
This application provides a REST API for managing items received from various content sources. Each source sends data in different formats, which is standardized and stored in a PostgreSQL database for further use.

### Key Components
1. **Controller Layer:**
   - `ItemController`: Exposes endpoints for CRUD operations and a bulk API.
2. **Service Layer:**
   - `ItemService`: Implements business logic and manages interactions between controllers and repositories.
3. **Repository Layer:**
   - `ItemRepository`: Interfaces with the database using Spring Data JPA.
4. **Mapper Layer:**
   - `ContentSourceMapper` (interface) and its implementations (`ContentSourceAMapper`, `ContentSourceBMapper`, `ContentSourceCMapper`) map source-specific data formats to the standard `Item` database model.

### Database Design
- **Table:** `items`
- **Fields:**
  - `id` (UUID): Auto-generated primary key.
  - `externalId` (VARCHAR): External identifier from the content source.
  - `title` (VARCHAR): Item title.
  - `author` (VARCHAR):  Item author.
  - `publishedYear` (INTEGER): Year of publication.
  - `type` (VARCHAR): Item type (e.g., article, video).
- **Indexes:**
  - Primary Index: `id` (Primary Key).
  - Additional Index: An index on `externalId` to optimize lookups based on external id.

### Design Patterns
- **Strategy Pattern:** Used for mapping data from different content sources (`ContentSourceAMapper`, `ContentSourceBMapper`, `ContentSourceCMapper`).
- **Factory Pattern:** Used to select the appropriate mapper based on ContentSource

### Error Handling
- **Invalid Content Source:** Returns HTTP 400 (Bad Request) with error message.
- **Item Not Found:** Returns HTTP 404 (Not Found) if the requested item does not exist.

### Logging
- Logs events like requests, errors, and successful operations for debugging and monitoring.

## Assumptions and Decisions
1. **Mapping Content Sources:**
   - Each content source sends data in a unique format. The `ContentSourceMapper` interface and its implementations standardize the data.
   - For example, `itemId` in `ContentSourceA` maps to `externalId` in the database.
2. **Extensibility:**
   - New content sources can be added by creating a new mapper that implements the `ContentSourceMapper` interface.
3. **Testing:**
   - Unit tests are written to validate the functionality of the service and mapper layers.
   - A separate test database instance can be created using Docker and configured in `application-test.properties`. 
4. **Database Schema:**
   - Hibernate's `update` strategy is used for development. Flyway is used for schema migrations management.

---

For any questions or further clarifications, please contact the author of the project.

