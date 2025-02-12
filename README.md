# **Pet Lovers Project**

## Description
This repository was created for a university project in the subject of Distributed Systems. The project focuses on developing a web application for managing animal shelters, veterinarians, adopters, and pets. The application provides a comprehensive platform for shelters to efficiently oversee their operations, including managing pet information, processing adoption requests, and coordinating with veterinarians. Additionally, system administrators have full control over user management, allowing them to create, modify, and delete user accounts as needed.

## Technologies Used
### Backend:
- **Spring Boot (3.3.4)**
    - Starter Web, Starter Data JPA, Starter Security, Starter Validation, Starter Mail
### Database:
- **PostgreSQL**
### Frontend:
- **Bootstrap**
- **Spring Boot Starter Thymeleaf**
- **Thymeleaf Extras Spring Security**
- **Thymeleaf Layout Dialect**

## User Roles
The system includes the following user roles:
- **Admin**: Manages users and watches over the system.
- **Shelter**: Adds and manages pets, processes adoptions.
- **Vet**: Views and updates medical records for pets.
- **Adopter**: Views and requests pet adoptions.

## Installation
### Prerequisites
Before you begin, ensure you have the following installed on your system:
- **Java 21+** (Check with `java -version`)
- **Maven 3.6.3** (Check with `mvn -version`)
- **PostgreSQL DB**
1. Clone the repository

   Open a terminal and run the following commands on a folder of your choice:
```
   git clone https://github.com/team/repo.git
   ```
2. **Connect to your DataBase**

   #### If you already have a PostgreSQL database running follow the below instructions:
    - **Update the `application.properties` with your database credentials on the following lines:
      `spring.datasource.username`, `spring.datasource.password`, `spring.datasource.url`**

   #### Otherwise you can install it from [here](https://dashboard.render.com/login)
    - **Login/Register to the website.**
    - **Create a new PostgreSQL database and add the needed fields.**
    - **Update the `application.properties` with your database credentials by replacing the following lines: `spring.datasource.username`, `spring.datasource.password`, `spring.datasource.url`**

3. **Run Project**
    
   ### You can run the project by a terminal...
    - **Run the project by executing the following command:**
   ```
   mvn spring-boot:run
   java -jar target/petLovers-0.0.1-SNAPSHOT.jar
    ```
   ### ... or you can run it by an IDE.
   - **Run it by an IDE, by executing the file `PetLovers` that's on the path `full-stack-dev-uni\src\main\java\pet\lovers`**

    
Make sure that you have the right dependencies and versions installed on your machine. You can find the dependencies and versions on the `pom.xml` file.

4.**Access to the App**
- **Open a browser and navigate to `http://localhost:8080`** and explore the application.

## Default Credentials
After setting up the database, you can log in using:
- **Admin**: 
    - **Username**: admin
    - **Password**: admin
- **Shelter**:
    - **Username**: shelter
    - **Password**: shelter
- **Vet**:
    - **Username**: vet
    - **Password**: vet
- **Adopter**:
    - **Username**: adopter
    - **Password**: adopter