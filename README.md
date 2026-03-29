# Hospital Management System

## Overview
This project is a **Hospital Management System** developed in **Spring Boot** by a team of 5 undergraduate students. The goal of the project is to provide a structured and easy-to-use system for scheduling an appointment at a hospital. We are currently in the process of completing this project. The system models a hospital environment with multiple interconnected entities, providing RESTful APIs to manage appointments, doctors, patients, departments, profiles, schedules, and time slots. Endpoints are tested using **Postman**.

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
| **Schedule**      | Repersents the doctor's schedule that a patient can look at to make an appointment.            |
| **TimeSlot**      | Represents a section of the doctor's schedule that a patient can choose to have their appointment.            |
| **Profile**       | Represents patient or doctor's account to the system.            |

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
  - The 8 main entities: Hosptial, Department, Doctor, Patient, Appointment, Profile, Schedule, & TimeSlot
  - Repositories for each entity
  - A HospitalController to handle API requests
  - A AppointmentService to handle all Appointment related business logic
  - A HospitalService to handle initializing of entities for Postman testing and hospital related business logic
  - A Postman workplace for testing requirements
 
Planned future enhancements include:
  - Developing a Presentation layer (user interfaces for Doctor and Patient entities)
