# Hospital Management System

## Overview
This project is a **Hospital Management System** developed in **Spring Boot** by a team of 5 undergraduate students. The goal of the project is to provide a structured and easy-to-use system for managing hospital operations. We are currently in the process of completing this project. The system models a hospital environment with multiple interconnected entities, providing RESTful APIs to manage appointments, doctors, patients, departments, insurance, and medical records. Endpoints are tested using **Postman**.

---

## Project Structure

The application is organized around **7 main entities**, each with its own repository:

| Entity            | Description                                                                 |
|------------------|-----------------------------------------------------------------------------|
| **Hospital**      | Represents a hospital, including its name, address, and departments.       |
| **Department**    | Represents hospital departments (e.g., Cardiology, Pediatrics).           |
| **Doctor**        | Represents doctors working in the hospital, linked to a department.       |
| **Patient**       | Represents patients registered in the hospital.                            |
| **Appointment**   | Represents appointments between patients and doctors.                     |
| **Medical Record**| Stores patient medical history and relevant health information.            |
| **Insurance**     | Represents patient insurance information and coverage details.            |

Each entity has a corresponding **Spring Data JPA repository** to manage database operations.

---

## Key Components

- **Controllers**  
  The project includes a **HospitalController** to handle API requests. Additional controllers for other entities can be added similarly.

- **Repositories**  
  Each entity has a dedicated repository interface extending `JpaRepository`, providing CRUD functionality.

- **Services** *(in progress)*  
  A service layer for business logic is planned and will be implemented in future updates.

- **Testing** *(in progress)*  
  Endpoints are being tested using **Postman**, with a complete Postman workspace to be developed as the project progresses.

--- 

## Project Status

  This system is currently under development. At this stage, the project includes: 
  - The 7 main entities: Hosptial, Department, Doctor, Patient, Appointment, Medical Record, Insurance
  - Repositories for each entity
  - A HospitalController to handle API requests
 
Planned future enhancements include:
  - Implementing a Service layer for business logic
  - Adding more controllers for other entities
  - Developing a presentation layer for users
  - Implementing Postman workspace
