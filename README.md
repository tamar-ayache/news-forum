[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/e99KNix9)
#  exercise number 5- Internet Programming Course
## Authors
# Tamar Ayache and Meir Getz
- Email: meirge@edu.hac.ac.il
- Email: tamaray@edu.hac.ac.il
## General Information
# News Management Web Application

## General Functionality
This project is a web application built using Spring Boot MVC. The application allows users to manage news articles, including creating, reading, updating, and deleting (CRUD) operations. It also supports user authentication and authorization, real-time updates using WebSocket, and session management.

## Features
- User Authentication and Authorization (Spring Security)
- CRUD Operations for News Articles
- Commenting System
- Role-based Access Control (User and Admin)
- Real-time updates using WebSocket
- Session management for storing temporary data
- Responsive UI with Bootstrap

## Project Structure
### Controllers
- `AuthController`: Manages user authentication (login, logout).
- `SignupController`: Manages user registration.
- `NewsController`: Manages news CRUD operations and displays the main news page.
- `CommentController`: Manages adding, deleting, and displaying comments.
- `WebSocketController`: Manages WebSocket connections and messaging.
- `ViewControllers`: Configures view controllers for Thymeleaf.

### Services
- `CustomUserDetailsService`: Implements `UserDetailsService` for Spring Security.
- `NewsService`: Business logic for news operations.
- `UserService`: Business logic for user operations.

### Repositories
- `UserRepository`: JPA repository for `User` entities.
- `NewsRepository`: JPA repository for `News` entities.
- `CommentRepository`: JPA repository for `Comment` entities.

### Entities
- `User`: Represents a user in the system.
- `News`: Represents a news article.
- `Comment`: Represents a comment on a news article.

## Role Management
User roles are managed to provide different levels of access to the application. There are two main roles:
- **USER**: A regular user with permissions to view and add news articles.
- **ADMIN**: An administrator with additional permissions to delete news articles.

During signup, users can select their role (USER or ADMIN). The role is stored in the session using the `@SessionAttributes` annotation in the `AuthController`. This ensures that the role is available throughout the user's session.

## HTML Pages
- `login.html`: Provides a login form for user authentication.
- `news.html`: Displays the latest news with real-time updates via WebSocket.
- `signup.html`: Provides a signup form for new user registration.

## Session Usage
Sessions are used to store temporary data, such as the user's role. This is done using the `@SessionAttributes` annotation in the `AuthController`.

## Dependency Injection
Dependency injection is implemented using the `@Autowired` annotation to inject beans into controllers and services. For example:
- `NewsService` is injected into `NewsController`.
- `NewsRepository` is injected into `NewsService`.

## JPA and MySQL
The project uses JPA (Java Persistence API) for ORM (Object-Relational Mapping) with a MySQL database. The main repositories are:
- `UserRepository`: Manages user-related database operations.
- `NewsRepository`: Manages news-related database operations.

## Running the Project
1. Clone the repository.
2. Set up the MySQL database and update the database configurations in `application.properties`.
3. Build the project using Maven: `mvn clean install`.
4. Run the application: `mvn spring-boot:run`.

## Known Bugs
- No known bugs at the moment.

## How to Initialize the Website
1. Make sure MySQL is running and the database `ex5` is created.
2. Update the database configurations in `application.properties`.
3. Run the application as described above.


## Usage
1. Signup: Register a new user at /signup.
2. Login: Login with your credentials at /login.
3. View News: View the latest news at /news.
4. Add News: (Admin only) Add a news article at /signupnews.
5. Update News: (Admin only) Update a news article at /edit/{id}.
6. Delete News: (Admin only) Delete a news article at /delete.
7. Add Comment: Add a comment to a news article.
8. View Comments: View comments associated with a news article.
## Admin Credentials
- Username: `admin`
- Password: `admin`
- Role: '1234'

## License
This project is licensed under the MIT License.

## Contributors
- Tamar Ayache
- Meir Getz
