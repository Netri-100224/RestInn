# RestInn – The Inn-side Job Done Right. 😉

# RestInn

[![Java](https://img.shields.io/badge/Java-17-blue?logo=java&logoColor=white)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.5.6-green?logo=spring&logoColor=white)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?logo=mysql&logoColor=white)](https://www.mysql.com/)
[![Maven](https://img.shields.io/badge/Maven-3.8.6-red?logo=apachemaven&logoColor=white)](https://maven.apache.org/)
[![JWT](https://img.shields.io/badge/JWT-json_web_token-orange?logo=json-web-tokens&logoColor=white)](https://jwt.io/)
[![Git](https://img.shields.io/badge/Git-F05032?logo=git&logoColor=white)](https://git-scm.com/)


## 📝 Description

RestInn is your all-in-one solution for hotel management and booking, designed to provide a seamless experience for users, managers, and administrators. Built with Java (Maven), RestInn offers comprehensive CRUD operations for managing hotel inventory and bookings, alongside advanced query capabilities to quickly find the perfect accommodation. Secure REST endpoints ensure data privacy and integrity across all user roles. Thoroughly tested for reliability, RestInn delivers hassle-free hotel bookings anytime, anywhere.


## 🚀 Tech Stack

| Layer         | Technology             |
|---------------|-------------------------|
| Backend       | Java 11+, Spring Boot   |
| Build Tool    | Maven                   |
| Database      | MySQL                   |
| Security      | Spring Security, JWT    |
| Testing       | JUnit, Mockito          |

---

## 🗃️ Database

- **RDBMS:** MySQL
- **Connection:** Configured via `application.properties`
- User: id, email, first_name, last_name, password, role

-Hotel: id, available_rooms, description, location, name, total_rooms, manager_id

-Booking: id, check_in_date, check_out_date, hotel_id, user_id

---
## 🔗 Relationships

One-to-Many (User → Booking): A customer can have multiple bookings.

One-to-Many (Hotel → Booking): A hotel can have multiple bookings.

One-to-One (Hotel → Manager): A hotel is managed by one user with role HOTEL_MANAGER.


## ✨ Key Features

### 👤 For Users:
- Register, login, and access JWT-secured APIs
- Search hotels by location, date range, and availability
- Book hotels, view bookings, cancel bookings

### 🧑‍💼 For Hotel Managers:
- Create, update, and delete hotels they manage
- View **all active bookings** for their hotels
- Monitor availability and manage room inventory

### 🛡️ For Admins:
- View, manage, or delete any hotel, user, or booking
- Assign manager roles to users
- Access all system-wide data

---

## 📦 Key Dependencies

```
jjwt-api: 0.11.2
jjwt-impl: 0.11.2
jjwt-orgjson: 0.11.2
```

## 📁 Project Structure

```
.
├── .mvn
│   └── wrapper
│       └── maven-wrapper.properties
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
    ├── main
    │   ├── java
    │   │   └── com
    │   │       └── hotel
    │   │           └── booking
    │   │               ├── BookingApplication.java
    │   │               ├── Config
    │   │               │   ├── CustomAccessHandler.java
    │   │               │   ├── JWTAuthenticationFilter.java
    │   │               │   └── SecurityConfig.java
    │   │               ├── Controller
    │   │               │   ├── AuthController.java
    │   │               │   ├── BookingController.java
    │   │               │   └── HotelController.java
    │   │               ├── DTO
    │   │               │   ├── AuthRequest.java
    │   │               │   ├── AuthResponse.java
    │   │               │   ├── BookingMapper.java
    │   │               │   ├── BookingRequest.java
    │   │               │   ├── BookingResponse.java
    │   │               │   ├── HotelRequest.java
    │   │               │   ├── HotelResponse.java
    │   │               │   ├── RegisterRequest.java
    │   │               │   └── UserResponse.java
    │   │               ├── Entity
    │   │               │   ├── Booking.java
    │   │               │   ├── Hotel.java
    │   │               │   ├── Role.java
    │   │               │   └── User.java
    │   │               ├── Exception
    │   │               │   ├── BookingNotFoundException.java
    │   │               │   ├── GlobalExceptionHandler.java
    │   │               │   ├── HotelNotFoundException.java
    │   │               │   ├── HotelRoomsNotAvailableException.java
    │   │               │   ├── IncorrectDateException.java
    │   │               │   ├── UserAlreadyExistsException.java
    │   │               │   └── UserNotFoundException.java
    │   │               ├── Repository
    │   │               │   ├── BookingRepository.java
    │   │               │   ├── HotelRepository.java
    │   │               │   └── UserRepository.java
    │   │               └── Service
    │   │                   ├── AuthService.java
    │   │                   ├── BookingService.java
    │   │                   ├── HotelService.java
    │   │                   ├── JWTService.java
    │   │                   └── UserService.java
    │   └── resources
    │       └── application.properties.template
    └── test
        └── java
            └── com
                └── hotel
                    └── booking
                        └── BookingApplicationTests.java
```

## 🛠️ Development Setup

### Java (Maven) Setup
1. Install Java (JDK 11+ recommended)
2. Install Maven
3. Install dependencies: `mvn install`
4. Run the project: `mvn exec:java` or check `pom.xml` for specific run commands


## 👥 Contributing

Contributions are welcome! Here's how you can help:

1. **Fork** the repository
2. **Clone** your fork: `git clone https://github.com/Netri-100224/RestInn.git`
3. **Create** a new branch: `git checkout -b feature/your-feature`
4. **Commit** your changes: `git commit -am 'Add some feature'`
5. **Push** to your branch: `git push origin feature/your-feature`
6. **Open** a pull request

Please ensure your code follows the project's style guidelines and includes tests where applicable.

---
