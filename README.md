# Hospital Management System

## Overview

This project is a **Hospital Management System** developed in **Spring Boot** by a team of 5 undergraduate students. The system provides a structured and user-friendly way to manage hospital operations, including scheduling appointments, managing doctors and patients, and tracking hospital occupancy.

The application models a hospital environment using interconnected entities and provides both:

* **RESTful APIs** (tested with Postman)
* **Web-based UI** (Doctor and Patient portals)

---

## Project Structure

The application is organized around **8 main entities**:

| Entity          | Description                                                          |
| --------------- | -------------------------------------------------------------------- |
| **Hospital**    | Represents a hospital, including capacity and departments.           |
| **Department**  | Represents hospital departments (e.g., Cardiology, Emergency).      |
| **Doctor**      | Represents doctors, each linked to a department and schedule.        |
| **Patient**     | Represents patients with personal and medical information.           |
| **Appointment** | Represents scheduled appointments between patients and doctors.      |
| **Schedule**    | Represents a doctor’s full schedule.                                 |
| **TimeSlot**    | Represents individual appointment slots within a schedule.           |
| **Profile**     | Handles authentication (login credentials for doctors and patients). |

Each entity is backed by a **Spring Data JPA repository** for database operations.

---

## Key Components

- **Controllers**
  - Multiple controllers handle both UI and REST endpoints
  - Example: `HospitalController`, `DoctorController`, authentication-related controllers

- **Services**
  - Business logic is handled in a dedicated service layer
  - Examples:
    - `AppointmentService`
    - `HospitalService`
    - `AuthService`
    - `ProfileService`

- **DTOs**
  - Used to transfer structured data between layers
  - Example:
    - `HospitalOccupancyDTO`

- **Security**
  - Implemented using **Spring Security**
  - Supports authentication and role-based access (Doctor / Patient)

- **Repositories**
  - Each entity has a `JpaRepository` for database operations

* **Frontend**

  * Built using **Thymeleaf**
  * Provides a Doctor dashboard with scheduling and occupancy view
  * Provides a Patient dashboard to schedule or edit existing appointments
  * Both Doctor or Patient can edit their username, email, or password

---

## RUNNING THE APPLICATION

### Start the application:

**Command line:**

```bash
./mvnw spring-boot:run
```

**OR**

**IntelliJ:**
Click the Run (▶) button in `HospitalSystemApplication`

---

Once running:

```text
http://localhost:8080
```

---

## LOGIN & TEST ACCOUNTS

Go to:

```text
http://localhost:8080/login
```

### Doctor Accounts

```text
doctor1 → doctor15
password
```

### Patient Accounts

```text
patient1 → patient60
password
```

---

## SEEDED DATA (AUTO-GENERATED)

On startup, the system automatically seeds:

* 1 Hospital
* 5 Departments:

  * Emergency
  * Radiology
  * Pediatrics
  * Cardiology
  * Oncology
* 15 Doctors (each with schedules)
* 60 Patients (with realistic demographic + medical data)
* 375 Time Slots (25 per doctor)
* ~90 Appointments (randomly distributed)

Each doctor has:

* 5 weekdays (Mon–Fri)
* 5 time slots per day

---

## FEATURES

* Doctor dashboard
* View and edit availability
* Appointments displayed with **full patient names**
* Hospital occupancy tracking
* Patient profiles with:

  * birth date
  * phone number
  * medical records
  * medications
* Appointment scheduling system
* Sorted time slots (by date and time)

---

## TESTING WITH POSTMAN

1. Open Postman collection
2. Go to **Authorization**
3. Select **Basic Auth**

Use:

```text
Username: doctor1
Password: password
```

---

### Example Endpoints

| Action               | Endpoint                              |
| -------------------- | ------------------------------------- |
| Get patients         | `GET /HMS/patients`                   |
| Get doctors          | `GET /HMS/doctors`                    |
| Schedule appointment | `POST /HMS/{id}/schedule-Appointment` |
| Edit appointment     | `POST /HMS/{id}/edit-Appointment`     |
| Get occupancy        | `GET /HMS/{id}/occupancy`             |

---

## DATABASE (H2 CONSOLE)

Access:

```text
http://localhost:8080/h2-console
```

Credentials:

```text
Username: sa
Password: password
```

---

## IMPORTANT NOTES

* The database is **in-memory**
  → resets on restart

* Authentication is required for most API endpoints

* Seed data is generated automatically at startup

---

## PROJECT STATUS

### Completed:

* All 8 entities fully implemented
* Repository layer complete
* Service layer implemented for core logic
* Doctor UI functional
* Appointment system working
* Occupancy tracking implemented
* Data seeding with realistic test data

---

## FUTURE ENHANCEMENTS

* Advanced appointment filtering
* Role-based access improvements
* Persistent database (replace H2)
* Analytics/dashboard features

---

## SUMMARY

This system simulates a real-world hospital environment with:

* structured scheduling
* realistic patient and doctor data
* appointment management
* authentication and role separation
* both API and UI interaction

---
