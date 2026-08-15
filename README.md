# Pilot Booking & Coupon Payment Management System (Backend)

**Author:** [Sonjoy Tripura](https://onuvob.github.io/onuvob/)  
**Contact:** [engr.sonjoy.tripura@gmail.com](mailto:engr.sonjoy.tripura@gmail.com)

---

## Project Details

### Application Name
**Vehicle Booking Management System**

### Description
A comprehensive RESTful API built with Spring Boot for managing a vehicle booking platform. The application features secure JWT-based authentication, role-based access control (Admin, Owner), and complete lifecycle management for vehicles, routes, pilots, coupons, and bookings. It includes administrative capabilities for approving/rejecting vehicles and bookings, assigning pilots, and managing system-wide coupons.

### Tech Stack
- **Backend:** Java, Spring Boot, Spring Security (JWT)
- **Database:** H2 (In-Memory), Spring Data JPA / Hibernate
- **Tools:** Lombok, SpringDoc OpenAPI (Swagger UI)
- **Build Tool:** Maven

### Setup & Configuration
- **Server Port:** `8080`
- **H2 Database Console:** `http://localhost:8080/h2-console`
    - **JDBC URL:** `jdbc:h2:mem:booking`
    - **Username:** `username`
    - **Password:** `password`
- **API Documentation (Swagger UI):** `http://localhost:8080/swagger-ui.html`
- **OpenAPI JSON:** `http://localhost:8080/api-docs`

---

## Default Users (Seeded Data)

The application automatically seeds the following users upon startup. You can use these credentials to log in via the `/api/auth/login` endpoint.

| Role | Email | Password | First Name | Last Name |
| :--- | :--- | :--- | :--- | :--- |
| **ADMIN** | `admin@sample.com` | `password123` | Super | Admin |
| **OWNER** | `owner1@sample.com` | `password123` | John | Owner 1 |
| **OWNER** | `owner2@sample.com` | `password123` | Kabir | Owner 2 |

---

## API Documentation

### Authentication API (`/api/auth`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/register` | Register a new user account | `RegisterRequest` |
| **POST** | `/login` | Authenticate user and return JWT tokens | `LoginRequest` |
| **POST** | `/refreshToken` | Generate a new access token using a refresh token | `RefreshTokenRequest` |
*(Note: Password reset endpoints have been omitted from this documentation as requested).*

### User API (`/api/user`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **GET** | `/list` | Retrieve a list of all users (Admin only) | - |
| **GET** | `/me` | Retrieve currently authenticated user's details | - |

### Booking Admin API (`/adminBookings`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **GET** | `/list` | Get paginated list of all bookings | Query Params (`RequestUtil`) |
| **PUT** | `/approve/{id}` | Approve a pending booking by ID | - |
| **PUT** | `/reject/{id}` | Reject a booking by ID | - |
| **PUT** | `/assignPilot` | Assign a pilot to a specific booking | `AssignPilotRequest` |

### Booking API (`/bookings`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/create` | Create a new vehicle booking | `BookingRequest` |
| **GET** | `/list` | Get paginated list of current user's bookings | Query Params (`RequestUtil`) |
| **GET** | `/get/{id}` | Get specific booking details by ID | - |

### Coupon Admin API (`/adminCoupons`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/create` | Create a new system coupon | `CouponRequest` |
| **GET** | `/list` | Get paginated list of all coupons | Query Params (`RequestUtil`) |

### Coupon API (`/coupons`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **GET** | `/list` | Get paginated list of user's available coupons | Query Params (`RequestUtil`) |

### Pilot Admin API (`/adminPilots`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **GET** | `/list` | Get paginated list of all pilots | Query Params (`RequestUtil`) |
| **POST** | `/create` | Add a new pilot to the system | `PilotRequest` |
| **PUT** | `/update/{id}` | Update pilot details by ID | `PilotRequest` |
| **DELETE** | `/delete/{id}` | Remove a pilot from the system | - |

### Route Admin API (`/adminRoutes`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/create` | Create a new travel route | `RouteRequest` |
| **PUT** | `/update/{id}` | Update route details by ID | `RouteRequest` |
| **DELETE** | `/delete/{id}` | Delete a route by ID | - |

### Route API (`/routes`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **GET** | `/list` | Get paginated list of all available routes | Query Params (`RequestUtil`) |
| **GET** | `/get/{id}` | Get specific route details by ID | - |

### Vehicle Admin API (`/adminVehicles`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **PUT** | `/approve/{id}` | Approve a pending vehicle registration | - |
| **PUT** | `/reject/{id}` | Reject a pending vehicle registration | - |
| **GET** | `/pending` | Get paginated list of pending vehicle approvals | Query Params (`RequestUtil`) |

### Vehicle API (`/vehicles`)
| Method | Endpoint | Description | Request Body |
| :--- | :--- | :--- | :--- |
| **POST** | `/create` | Register a new vehicle | `VehicleRequest` |
| **PUT** | `/update/{id}`* | Update vehicle details | `VehicleRequest` |
| **DELETE** | `/delete/{id}` | Delete a vehicle by ID | - |
| **GET** | `/selfList` | Get paginated list of current user's vehicles | Query Params (`RequestUtil`) |

*\*Note: The controller maps `/update` but expects a `@PathVariable Long id`. Ensure you pass the ID in the path (e.g., `/update/1`) when testing.*

---

## Data Transfer Objects (Request Bodies)

### RegisterRequest
```json
{
  "firstName": "string (Required)",
  "lastName": "string (Required)",
  "email": "user@example.com (Required, Valid Email)",
  "password": "string (Required, Min 6 characters)",
  "phone": "string (Optional)"
}