# 🗓️ Daily Task Spec Sheets for Project (11/27–12/7)

---

## Day 1: 11/27

### Database Setup & Sample Data
- **Objective:** Design and initialize the MariaDB schema.
- **Steps:**
  1. Install MariaDB locally or on a server.
  2. Create a new database for the project.
  3. Draft SQL scripts for tables: users, restaurants, favorites, route_plans, route_plan_items.
  4. Insert at least 10 sample restaurants.
- **Tips:** Use plan.md schema; tools like DBeaver/phpMyAdmin help.
- **Deliverables:** SQL scripts, sample data, connection info.

---

## Day 2: 11/28

### Database Setup & Sample Data
- **Objective:** Finalize schema and sample data.
- **Steps:**
  1. Test table creation and sample inserts.
  2. Add more sample data if needed.
  3. Document schema for backend team.
- **Deliverables:** Final schema, sample data, documentation.

### Backend API (Flask) Core Endpoints
- **Objective:** Set up Flask project and DB connection.
- **Steps:**
  1. Create Flask project (use virtualenv).
  2. Install Flask, Flask-RESTful/RESTX, SQLAlchemy, MariaDB connector.
  3. Set up basic structure (app.py, models, routes).
  4. Connect to MariaDB using SQLAlchemy.
- **Tips:** Use Flask and SQLAlchemy docs.
- **Deliverables:** Flask project, DB connection, initial commit.

---

## Day 3: 11/29

### Database Setup & Sample Data
- **Objective:** Ensure DB is ready for backend.
- **Steps:**
  1. Final review of schema and data.
  2. Share connection info with backend team.
- **Deliverables:** Ready-to-use DB.

### Backend API (Flask) Core Endpoints
- **Objective:** Implement `/restaurants` and `/auth` endpoints.
- **Steps:**
  1. Define models for users and restaurants.
  2. Implement GET `/restaurants` (list/search).
  3. Implement POST `/auth/register` and `/auth/login`.
  4. Test endpoints with sample data.
- **Tips:** Use Flask-RESTful for endpoints.
- **Deliverables:** Working endpoints, tested with sample data.

### Android App Project Setup & Login/Register
- **Objective:** Initialize Android project and build auth screens.
- **Steps:**
  1. Create new Android Studio project.
  2. Set up basic navigation and UI theme.
  3. Build login and register screens.
  4. Prepare Retrofit for API calls.
- **Tips:** Use Kotlin and Android docs.
- **Deliverables:** Android project, login/register UI.

---

## Day 4: 11/30

### Backend API (Flask) Core Endpoints
- **Objective:** Implement `/favorites` and `/route-plans` endpoints.
- **Steps:**
  1. Define models for favorites and route plans.
  2. Implement GET/POST/DELETE for `/favorites`.
  3. Implement GET/POST/DELETE for `/route-plans`.
  4. Test endpoints.
- **Deliverables:** Working endpoints, tested.

### Android App Project Setup & Login/Register
- **Objective:** Connect login/register screens to backend.
- **Steps:**
  1. Implement API calls for login/register using Retrofit.
  2. Handle authentication tokens.
  3. Test login/register flow.
- **Deliverables:** Auth flow working in app.

### Restaurant Search & Display (App + API)
- **Objective:** Build restaurant search UI and backend support.
- **Steps:**
  1. Design search UI in Android app.
  2. Implement API call to `/restaurants`.
  3. Display restaurant list in app.
- **Deliverables:** Search UI, API integration.

---

## Day 5: 12/01

### Backend API (Flask) Core Endpoints
- **Objective:** Polish and test all endpoints.
- **Steps:**
  1. Add error handling and validation.
  2. Write unit tests for endpoints.
  3. Document API.
- **Deliverables:** Robust, documented API.

### Restaurant Search & Display (App + API)
- **Objective:** Finalize search and display features.
- **Steps:**
  1. Add filters (cuisine, location).
  2. Polish UI/UX.
  3. Test with real data.
- **Deliverables:** Search feature complete.

### Favorites Feature (App + API)
- **Objective:** Implement favorites in app and backend.
- **Steps:**
  1. Add favorite/unfavorite buttons in app.
  2. Connect to `/favorites` API.
  3. Display favorite restaurants.
- **Deliverables:** Favorites feature working.

---

## Day 6: 12/02

### Backend API (Flask) Core Endpoints
- **Objective:** Final review and bug fixes.
- **Steps:**
  1. Fix any issues found in testing.
  2. Update documentation.
- **Deliverables:** Finalized backend.

### Restaurant Search & Display (App + API)
- **Objective:** Final polish and bug fixes.
- **Steps:**
  1. Address UI/UX feedback.
  2. Ensure smooth API integration.
- **Deliverables:** Polished search feature.

### Favorites Feature (App + API)
- **Objective:** Polish and test favorites.
- **Steps:**
  1. Test edge cases (add/remove).
  2. Improve UI/UX.
- **Deliverables:** Polished favorites feature.

### Navigation Integration (Google Maps)
- **Objective:** Integrate Google Maps SDK.
- **Steps:**
  1. Set up Google Maps in Android app.
  2. Display map and user location.
  3. Prepare for route display.
- **Tips:** Use Google Maps SDK docs.
- **Deliverables:** Map integration working.

---

## Day 7: 12/03

### Restaurant Search & Display (App + API)
- **Objective:** Final testing and bug fixes.
- **Steps:**
  1. Test with different search queries.
  2. Fix any remaining issues.
- **Deliverables:** Search feature ready.

### Favorites Feature (App + API)
- **Objective:** Final testing and bug fixes.
- **Steps:**
  1. Test with multiple users.
  2. Fix any remaining issues.
- **Deliverables:** Favorites feature ready.

### Navigation Integration (Google Maps)
- **Objective:** Show route to selected restaurant.
- **Steps:**
  1. Implement route display from user to restaurant.
  2. Test navigation feature.
- **Deliverables:** Navigation working.

---

## Day 8: 12/04

### Favorites Feature (App + API)
- **Objective:** Final polish and documentation.
- **Steps:**
  1. Document feature for users.
  2. Final UI tweaks.
- **Deliverables:** Favorites feature complete.

### Navigation Integration (Google Maps)
- **Objective:** Polish and test navigation.
- **Steps:**
  1. Test with different restaurants.
  2. Fix any issues.
- **Deliverables:** Navigation feature complete.

### Route Planning Feature
- **Objective:** Start route planning feature.
- **Steps:**
  1. Design UI for route planning.
  2. Implement backend support for route plans.
- **Deliverables:** Initial route planning feature.

---

## Day 9: 12/05

### Navigation Integration (Google Maps)
- **Objective:** Final polish and bug fixes.
- **Steps:**
  1. Test with edge cases.
  2. Finalize documentation.
- **Deliverables:** Navigation feature finalized.

### Route Planning Feature
- **Objective:** Implement route planning in app and backend.
- **Steps:**
  1. Allow users to add restaurants to a plan.
  2. Save and retrieve plans via API.
  3. Display planned route in app.
- **Deliverables:** Route planning feature working.

---

## Day 10: 12/06

### Route Planning Feature
- **Objective:** Finalize route planning feature.
- **Steps:**
  1. Polish UI/UX.
  2. Test with multiple plans.
  3. Fix any issues.
- **Deliverables:** Route planning feature complete.

### Testing & Bug Fixes
- **Objective:** Test all features and fix bugs.
- **Steps:**
  1. Run unit and integration tests.
  2. Fix bugs found.
  3. Prepare for deployment.
- **Deliverables:** All features tested and bug-free.

### Deployment & Documentation
- **Objective:** Prepare for deployment and document project.
- **Steps:**
  1. Write user and developer documentation.
  2. Prepare backend for deployment.
- **Deliverables:** Documentation, deployment-ready backend.

---

## Day 11: 12/07

### Testing & Bug Fixes
- **Objective:** Final testing and bug fixes.
- **Steps:**
  1. Final round of testing.
  2. Fix last-minute bugs.
- **Deliverables:** Stable, tested app and backend.

### Deployment & Documentation
- **Objective:** Deploy backend and release app.
- **Steps:**
  1. Deploy backend to server/cloud.
  2. Prepare app for Play Store release.
  3. Finalize all documentation.
- **Deliverables:** Deployed backend, app ready for release, complete documentation.

---

*Update this spec sheet as needed during development to reflect progress and changes.*
