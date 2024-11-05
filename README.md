Training Center Management - REST API

Project Context

    A training center aims to digitalize the management of its training programs through a REST API. This platform allows for managing learners, Instructors, trainings, and classes. The application follows a RESTful architecture to provide standardized access to data.

Main Features

    Learner Management: Registration, modification, deletion, and display (one or multiple).
    Instructor Management: Creation, modification, deletion, and display (one or multiple).
    Training Management: Scheduling, modification, deletion, and display (one or multiple).
    Classes Management: Creation, modification, deletion, and display (one or multiple).

Structure of Main Entities

    Learner: first name, last name, email, level, training, class
    Instructor: first name, last name, email, specialty, training, class
    Class: name, room number
    Training: title, level, prerequisites, minimum capacity, maximum capacity, start date, end date, trainer, learners, status (PLANNED, IN_PROGRESS, COMPLETED, CANCELED)

Application Architecture

    Entities: Defined with JPA and validation.
    Repositories: Use JpaRepository for data access.
    Services: Business logic for each entity.
    Controllers: Provide REST endpoints.
    Exceptions: Centralized error handling.
    Tests: Unit and integration tests.

Technologies Used
Backend

    Spring Boot
        Project initialization via Spring Initializer.
        Configuration via application.properties.
        Profiles (dev, prod):
            application-dev.properties for the development environment.
            application-prod.properties for the production environment.
        Use of annotations for IoC and DI.

    Spring Data JPA
        Repositories using JpaRepository.
        Custom query methods with @Query and @Param.
        Implementation of pagination.

    REST API
        CRUD endpoints for each entity.
        Use of Swagger for API documentation.
        Testing via Postman and/or Swagger.

    Exception Management
        Specific classes to handle errors.
Databases

    H2: Used in development (dev).
    PostgreSQL: Used in production (prod).

Others

    Java 8: Lambda expressions, Stream API, Java Time API, Optional.
    Maven: Dependency management.
    JUnit and Mockito: For unit and integration testing.
    JaCoCo: For code coverage.
    Lombok: Reduction of boilerplate code.
    Logger: Logging system with SLF4J.

Specific Features

    Complete CRUD: Create, read, update, and delete each entity.
    Validation: Use of validation annotations (@NotNull, @Valid, etc.) to ensure data consistency.
    Spring Scopes: Use of Singleton and Prototype as needed.
    Swagger: Interactive documentation of the API.
    Logging: Use of a logging system to track application activities.

Deployment Instructions

Clone the project:

       git clone https://github.com/JavaAura/LAMIAA_TERMOUSSI_S3_B2_-ProfessionalTraining

Author and Contact Information

    Termoussi Lamiaa 
    Email: lamiaa3105@gmail.com
