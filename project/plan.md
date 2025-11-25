# Restaurant Search & Navigation App — Implementation Plan

## Project Overview

A mobile application for searching restaurants, marking favorites, and planning navigation routes. The system consists of three main components:

1. **Android User App** — User interface for search, favorites, navigation, and route planning.
2. **Backend Server (Python + Flask)** — REST API for data management, authentication, and business logic.
3. **Database (MariaDB)** — Stores restaurant, user, favorites, and route plan data.

---

## Architecture

```
[Android App] <——> [Flask Backend API] <——> [MariaDB Database]
```

- Android app communicates with backend via HTTP REST API.
- Backend interacts with MariaDB for persistent storage.

---

## Technology Stack

- **Android App**: Kotlin (preferred), Android Studio, Retrofit, Google Maps SDK
- **Backend**: Python, Flask, Flask-RESTful/RESTX, SQLAlchemy, JWT authentication
- **Database**: MariaDB

---

## Feature Breakdown

- **Restaurant Search**: Search/filter by name, location, cuisine
- **Favorites**: Mark/unmark restaurants, view favorites
- **Navigation**: Show route from user location to restaurant (Google Maps integration)
- **Route Planning**: Create/edit a list of restaurants to visit in order, view navigation for the list
- **User Authentication**: Register, login, manage user data

---

## Step-by-Step Implementation Plan

### Phase 1: Setup & Basic Functionality

1. **Database Design**
   - Design schema, create tables in MariaDB
   - Populate with sample restaurant data

2. **Backend API**
   - Set up Flask project structure
   - Implement endpoints:
     - `/restaurants` (GET, search/filter)
     - `/favorites` (GET, POST, DELETE)
     - `/route-plans` (GET, POST, DELETE)
     - `/auth` (register/login)
   - Connect to MariaDB using SQLAlchemy

3. **Android App**
   - Set up project in Android Studio
   - Implement login/register screens
   - Implement restaurant search UI
   - Integrate with backend API using Retrofit
   - Display restaurant list

### Phase 2: Advanced Features

4. **Favorites**
   - Add favorite/unfavorite functionality in app and backend
   - Show favorite restaurants in a separate list

5. **Navigation**
   - Integrate Google Maps SDK
   - Show route from user location to selected restaurant

6. **Route Planning**
   - Allow users to create/edit a list of restaurants to visit
   - Backend stores route plans
   - App displays route plan and navigation for each step

### Phase 3: Polish & Testing

7. **Authentication & Security**
   - Secure API endpoints (JWT tokens)
   - Hash passwords in database

8. **Testing**
   - Unit and integration tests for backend
   - UI and functional tests for Android app

9. **Deployment**
   - Deploy backend (Heroku, AWS, etc.)
   - Prepare app for release (Play Store)

---

## Suggestions & Best Practices

- Use RESTful API design and versioning
- Validate and sanitize all inputs
- Secure user data and API endpoints
- Document API endpoints (Swagger)
- Automate tests for backend and app
- Make UI intuitive and responsive

---

## Example Database Schema

```sql
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE,
    password_hash VARCHAR(255),
    email VARCHAR(100) UNIQUE
);

CREATE TABLE restaurants (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    address VARCHAR(255),
    lat DOUBLE,
    lng DOUBLE,
    cuisine VARCHAR(50)
);

CREATE TABLE favorites (
    user_id INT,
    restaurant_id INT,
    PRIMARY KEY (user_id, restaurant_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);

CREATE TABLE route_plans (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    name VARCHAR(100),
    created_at DATETIME,
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE route_plan_items (
    id INT PRIMARY KEY AUTO_INCREMENT,
    route_plan_id INT,
    restaurant_id INT,
    order_num INT,
    FOREIGN KEY (route_plan_id) REFERENCES route_plans(id),
    FOREIGN KEY (restaurant_id) REFERENCES restaurants(id)
);
```

---

## Next Steps

1. Design database schema and set up MariaDB
2. Set up Flask backend and connect to database
3. Start Android app project and connect to backend
4. Implement features iteratively, testing as you go

---

*This plan serves as a roadmap for your team. Update as needed during development.*
