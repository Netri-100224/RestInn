# RestInn



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
- **Schema includes**:
  - `User` (id, name, email, password, role)
  - `Hotel` (id, name, location, roomsAvailable, pricePerNight, rating, managerId)
  - `Booking` (id, userId, hotelId, checkInDate, checkOutDate, status)

---

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
