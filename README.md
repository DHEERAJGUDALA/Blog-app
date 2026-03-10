# Blog App

A simple Spring Boot blog application built to practice Spring Data JPA, entity relationships, derived queries, JPQL, pagination, and PostgreSQL integration.

## Overview

This project models a basic blog domain with:
- `User` as the author of posts and comments
- `Post` with publish status, timestamps, comments, and tags
- `Comment` linked to both a post and a user
- `Tag` connected to posts through a many-to-many relationship

The current codebase focuses on the persistence layer and database interactions. It does not expose REST endpoints yet.

## Tech Stack

- Java 17
- Spring Boot 4.0.3
- Spring Data JPA
- PostgreSQL
- Lombok
- Maven

## Features

- JPA entity mapping with real-world relationships
- One-to-many, many-to-one, and many-to-many associations
- Automatic timestamp handling using JPA lifecycle callbacks
- Derived query methods in Spring Data JPA
- Custom JPQL queries
- Pagination and sorting support
- Startup data seeding with sample users, posts, comments, and tags
- Basic integration test for Spring context loading

## Project Structure

```text
src/main/java/com/blog/blog_app
|- entity/
|  |- User.java
|  |- Post.java
|  |- Comment.java
|  \- Tag.java
|- repository/
|  |- UserRepository.java
|  |- PostRepository.java
|  |- CommentRepository.java
|  \- TagRepository.java
|- runner/
|  \- DataLoader.java
\- BlogAppApplication.java
```

## Domain Model

### User
Stores author details such as name, email, password, and creation timestamp.

### Post
Represents a blog post with title, content, publish status, author, timestamps, comments, and tags.

### Comment
Represents comments written by users on posts.

### Tag
Represents labels such as `java`, `spring`, and `jpa` that can be attached to multiple posts.

## Relationships

- One `User` can author many `Post` records
- One `Post` can have many `Comment` records
- One `User` can author many `Comment` records
- One `Post` can have many `Tag` records
- One `Tag` can belong to many `Post` records

## Database Configuration

The application is configured to use PostgreSQL with the following defaults:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/blogdb
spring.datasource.username=postgres
spring.datasource.password=postgres
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

Before running the application, create a PostgreSQL database named `blogdb`.

Example:

```sql
CREATE DATABASE blogdb;
```

If your local PostgreSQL username or password is different, update `src/main/resources/application.properties`.

## Getting Started

### Prerequisites

- Java 17 or later
- Maven 3.9+ or the included Maven Wrapper
- PostgreSQL running locally on port `5432`

### Run Locally

1. Clone the repository.
2. Create the `blogdb` database in PostgreSQL.
3. Update database credentials in `src/main/resources/application.properties` if needed.
4. Start the application:

```bash
./mvnw spring-boot:run
```

On Windows:

```powershell
.\mvnw.cmd spring-boot:run
```

### Run Tests

```bash
./mvnw test
```

On Windows:

```powershell
.\mvnw.cmd test
```

## Seed Data

On startup, the `DataLoader` component inserts sample data and runs repository examples.

Seeded records include:
- Users: `Sai`, `Rahul`
- Tags: `java`, `spring`, `jpa`
- Posts:
  - `Learning JPA`
  - `Spring Boot Guide`
  - `Draft Post` (later updated and deleted inside the loader flow)
- Comments on the sample posts

Note: because `DataLoader` inserts records on every startup, re-running the application may create duplicate seed data unless you reset the database or change the seeding logic.

## Repository Capabilities

The repositories currently demonstrate:
- Find users by email or partial name
- Find published and unpublished posts
- Find posts by author ID or author email
- Search posts by title
- Count posts by author
- Find comments by post or author
- Count comments per post
- Search tags by name
- Pagination and sorting for posts, users, and comments

## Learning Focus

This project is useful for understanding:
- How JPA entity relationships are mapped in Spring Boot
- When to use derived queries vs JPQL
- Lazy loading basics
- Cascade options and orphan removal
- Pagination with `Pageable` and `Page`

## Current Limitations

- No REST controllers or frontend yet
- No service layer yet
- No API documentation yet
- Credentials are stored in `application.properties` for local development
- `DataLoader` is intended for learning/demo use, not production seeding

## Future Improvements

- Add REST APIs for users, posts, comments, and tags
- Introduce DTOs and validation
- Add a service layer
- Add exception handling and API responses
- Add Docker support
- Replace hardcoded database credentials with environment variables
- Expand automated test coverage

## Author

Built as a Spring Boot and JPA practice project.
