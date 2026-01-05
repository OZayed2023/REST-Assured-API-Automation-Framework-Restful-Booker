# Restful Booker API Automation Framework

A professional API testing framework built to validate the **Restful Booker Hotel Booking System** using both manual and automated testing approaches.

This project demonstrates real-world API testing practices including requirements analysis, test design, negative testing, end-to-end scenarios, automation framework design, and professional reporting.

---

## 1. Project Overview

The Restful Booker API is a public RESTful service that simulates a real hotel booking management system.  
This project focuses on validating backend business logic through structured API testing rather than UI testing.

The framework follows a **testing-first approach**, starting with requirements analysis and manual testing, then evolving into a scalable and maintainable automation framework.

---

## 2. Project Objectives

- Validate core booking functionalities
- Verify input validation and error handling
- Ensure secured endpoints enforce authentication rules
- Detect functional defects through negative testing
- Validate complete end-to-end business workflows
- Build a reusable and maintainable API automation framework

---

## 3. System Under Test (SUT)

The system under test is the **Restful Booker API**, which exposes REST endpoints for managing hotel bookings.

The API supports:
- Authentication
- Booking creation
- Booking retrieval
- Booking update
- Booking deletion
- Health checking

### Core Endpoints

| Endpoint | Method | Description |
|--------|--------|------------|
| `/auth` | POST | Generate authentication token |
| `/booking` | POST | Create new booking |
| `/booking/{id}` | GET | Retrieve booking details |
| `/booking/{id}` | PUT | Update existing booking |
| `/booking/{id}` | DELETE | Delete booking |
| `/ping` | GET | Health check |

---

## 4. Requirements Analysis

Based on official API documentation and exploratory testing, the following business rules were identified:

- Authentication is mandatory for **update** and **delete** operations
- Booking requests must include mandatory fields:
  - `firstname`
  - `lastname`
  - `totalprice`
  - `bookingdates`
- `totalprice` must be numeric
- Invalid input data should be rejected
- Proper HTTP status codes (4xx) should be returned for invalid requests

---

## 5. Test Strategy

### Test Types Applied
- Functional API Testing
- Positive Testing (Happy Path)
- Negative Testing (Validation and Error Handling)
- End-to-End Business Scenarios

### Scope

**In Scope**
- Authentication APIs
- Booking Management APIs
- Positive & Negative scenarios
- End-to-End workflows

**Out of Scope**
- UI testing
- Performance testing
- Security testing beyond authentication

---

## 6. Manual Testing (Postman)

Manual testing was performed first using **Postman** to deeply understand system behavior before automation.

Test cases were organized by functionality and designed to represent real business scenarios.

### Test Case Naming Convention

- `Pxx` – Positive Test Cases
- `Nxx` – Negative Test Cases
- `Sxx` – End-to-End Scenarios

**Examples**
- `P01_CreateBooking_Valid`
- `N04_CreateBooking_Totalprice_As_Text`
- `S01_Create_Update_Delete_Booking`

---

## 7. End-to-End Scenarios

End-to-end scenarios validate complete business workflows across multiple API calls.

A typical scenario includes:
1. Generate authentication token
2. Create booking
3. Retrieve booking
4. Update booking
5. Delete booking
6. Verify deletion

These scenarios ensure system consistency and business flow validation.

---

## 8. Automation Framework

### Technology Stack

- Java
- Rest Assured
- TestNG
- Maven
- Allure Report

### Framework Design Principles

- Layered architecture
- Separation of concerns
- Readable and maintainable code
- Reusable API interaction components

### Project Structure
```text
src/test/java
├── apis        # API interaction layer
├── models     # Request and response POJOs
├── testcases  # Positive, Negative, and E2E tests
└── utils      # Routes, helpers, and test data utilities
```
## 9. Test Execution

All automated tests are executed using **Maven** and **TestNG**.

```bash
mvn clean test
```
## 10. Reporting (Allure)

Allure reporting is integrated to generate professional execution reports including:

- Passed and failed test cases
- Execution steps
- Request and response payloads
- Clear evidence for failures

## 11. Defect Analysis

Multiple real defects were identified through negative testing:

- **Bug 1:** System accepts empty `firstname` field (**High severity**)
- **Bug 2:** System accepts `totalprice` as text instead of number (**High severity**)
- **Bug 3:** Deleting non-existing booking returns HTTP 500 instead of 4xx (**Medium severity**)

## 12. Documentation

A full technical presentation covering system overview, test strategy, manual testing, automation architecture, and defect analysis is included within the project documentation.

## 13. Author

**Omar Zayed**  
Software Testing Engineer  
ITI – Software Testing Track

## 14. Conclusion

This project represents a complete real-world API testing lifecycle.  
It demonstrates strong testing fundamentals, structured analysis, and the ability to design scalable and maintainable automation frameworks while identifying real system defects.
