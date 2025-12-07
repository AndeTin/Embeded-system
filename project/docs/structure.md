# Foodmap Implementation Structure

## 1. System Overview
- **Goal**: Deliver a restaurant discovery, bookmarking, and navigation experience backed by a Flask + MariaDB service and an Android (Kotlin) client.
- **Core Components**:
  - Android app (UI, UX, state management, Retrofit networking, Google Maps SDK integration).
  - Flask backend (REST API, authentication, business logic, data validation, SQLAlchemy ORM, JWT-based security).
  - MariaDB datastore (normalized schema, indices, seed data, migrations/DDL management).
- **Integration**: Android client communicates with Flask via HTTPS JSON APIs; Flask persists and queries data through SQLAlchemy to MariaDB.

## 2. Service Implementation Plans

### 2.1 Android Client (Kotlin)
1. **Project Foundation**
   - Scaffold project with Compose Material3 (or XML if preferred) using Android Studio template.
   - Configure Gradle for Retrofit, OkHttp, Gson/Moshi, Hilt (optional), Google Maps SDK, and Jetpack Navigation.
   - Establish `build.gradle` build flavors/environments (dev, prod) with base URLs stored in `BuildConfig`.
2. **Architecture & State Management**
   - Adopt MVVM structure with ViewModel + Repository layers.
   - Set up a shared `ApiService` interface, Retrofit client, and DataStore for JWT token persistence.
   - Create navigation graph covering Auth, Restaurant List, Restaurant Detail, Favorites, Route Planner, Settings.
3. **Authentication Module**
   - Screens: splash (token check), login, register.
   - ViewModels handle form validation, error states, token storage, and navigation to main flow on success.
4. **Restaurant Search Module**
   - Implement search screen with filter controls (name, class, zipcode) and result list using LazyColumn/RecyclerView.
   - Integrate infinite scroll/pagination once backend supports it.
   - Show detail page with map preview and CTA to favorite or start navigation.
5. **Favorites Module**
   - Build favorites list screen backed by `/favorites` endpoints.
   - Support optimistic UI updates and offline cache (Room) if time permits.
6. **Route Planning Module**
   - UI to create/edit named plans, reorder stops via drag-drop, and launch navigation per stop.
   - Persist plan changes via `/route-plans` endpoints; maintain local draft state for unsaved edits.
7. **Navigation/Maps Module**
   - Integrate Google Maps SDK with API key stored securely (gradle properties + xml resource).
   - Implement map screen showing user location and selected restaurant markers.
   - Use Google Directions API (client-side or backend proxy) to compute routes.
8. **Cross-Cutting Features**
   - Centralized error handling/toasts/snackbars.
   - Dark mode and accessibility compliance.
   - Instrumented tests (Espresso) for critical flows; unit tests for ViewModels with mocked repositories.
9. **Packaging & Release Prep**
   - Configure signing configs, build variants, and Play Store assets (if needed).
   - Build CI/CD (GitHub Actions) for lint, unit tests, and bundle generation.

### 2.2 Flask Backend
1. **Environment Setup**
   - Create virtual environment; install dependencies (`Flask`, `Flask-RESTX`, `SQLAlchemy`, `PyMySQL`, `Flask-JWT-Extended`, `Marshmallow`).
   - Organize project structure: `app/__init__.py`, `models`, `schemas`, `routes`, `services`, `config`.
2. **Configuration Management**
   - Load environment variables via `.env` with fallback defaults.
   - Configure SQLAlchemy engine (MariaDB URI), JWT secret, and CORS settings (Flask-CORS).
3. **Database Layer**
   - Model classes for `User`, `Restaurant`, `Favorite`, `RoutePlan`, `RoutePlanItem` matching schema.
   - Implement Alembic or custom migration scripts for schema management.
   - Seed command (Flask CLI) to load initial restaurant dataset.
4. **Authentication Service**
   - Endpoints: `POST /auth/register`, `POST /auth/login`, optional `POST /auth/refresh`.
   - Use Marshmallow schemas for request validation; hash passwords with `werkzeug.security`.
   - Issue JWTs with custom claims (user_id, username); enforce auth via decorators.
5. **Restaurant Service**
   - Endpoint `GET /restaurants` supporting pagination, text search, class filter, bbox/zipcode filter.
   - Endpoint `GET /restaurants/<id>` for detailed view.
   - Optional admin `POST/PUT/DELETE` for management (future scope).
6. **Favorites Service**
   - Endpoints `GET /favorites`, `POST /favorites`, `DELETE /favorites/<restaurant_id>`.
   - Ensure per-user scoping and conflict handling (duplicate prevention, graceful delete).
7. **Route Plan Service**
   - Endpoints `GET /route-plans`, `POST /route-plans`, `GET /route-plans/<id>`, `PUT/PATCH` for item updates, `DELETE`.
   - Manage plan items order; consider transaction boundaries to keep plan/items consistent.
8. **Navigation Helper (Optional)**
   - Proxy endpoint `/navigation/route` calling Google Directions API to avoid exposing API key to client.
   - Cache responses (Redis/Flask-Caching) to reduce API quota usage.
9. **Error Handling & Observability**
   - Register global error handlers returning JSON with consistent structure.
   - Instrument logging (structured JSON) and request metrics (Prometheus/StatsD if available).
10. **Testing & Documentation**
    - Pytest suite covering models, services, and route tests (with Flask test client & DB fixtures).
    - Generate API docs via Swagger UI (Flask-RESTX) at `/docs`.
    - Configure CI to run unit tests, lint (Flake8/Black), and security checks (Bandit).

### 2.3 MariaDB Layer
1. **Schema Definition**
   - Create DDL aligning with plan (`users`, `restaurants`, `favorites`, `route_plans`, `route_plan_items`).
   - Add foreign keys, cascading rules, unique constraints, and indexes (`restaurants.name`, `restaurants.class`, `favorites.user_id`).
   - Consider geospatial extensions (POINT) for latitude/longitude if future optimization needed.
2. **Data Management**
   - Seed dataset via SQL dump or ETL script.
   - Establish migration strategy (Alembic generating SQL for MariaDB). Keep migrations versioned.
   - Backup policy: nightly dumps, local development resets via script.
3. **Performance & Security**
   - Enable read replicas if scaling; for now, configure connection pooling via SQLAlchemy.
   - Use least-privilege DB user for application; separate admin credentials for maintenance.
   - Store credentials in environment variables/secret manager.

## 3. Data Model Snapshot
- **User**: `id`, `username`, `email`, `password_hash`, timestamps.
- **Restaurant**: `id`, `name`, `description`, `address`, `lat`, `lng`, `class`, `opentime`, metadata.
- **Favorite**: composite key `(user_id, restaurant_id)`.
- **RoutePlan**: `id`, `user_id`, `name`, `created_at`.
- **RoutePlanItem**: `id`, `route_plan_id`, `restaurant_id`, `order_num`.
- Extendable fields: tags, ratings, photos, external links.

## 4. Dataflows & Workflows

### 4.1 Authentication Flow
1. User submits credentials via Android app.
2. App calls `POST /auth/login` with JSON payload.
3. Flask validates input, verifies password, issues access + refresh JWT.
4. Response returns tokens; app stores in encrypted DataStore.
5. Subsequent API requests attach `Authorization: Bearer <token>` header.
6. Backend validates JWT on protected routes; returns data or 401.

### 4.2 Restaurant Search Flow
1. User enters filters in search screen.
2. App constructs query params and calls `GET /restaurants`.
3. Backend builds SQLAlchemy query, applies filters, paginates results.
4. Response JSON includes list + pagination metadata.
5. App updates UI list; selecting an item fetches detail if needed.

### 4.3 Favorites Management Flow
1. User taps favorite toggle in restaurant detail.
2. App sends `POST /favorites` (add) or `DELETE /favorites/<id>`.
3. Backend validates restaurant exists, enforces uniqueness, updates DB.
4. Backend returns updated favorite status; app updates UI list and caches.
5. Favorites screen `GET /favorites` pulls full list for display.

### 4.4 Route Planning Flow
1. User creates plan in app (plan name + selected restaurants).
2. App sends `POST /route-plans` with plan metadata and ordered items.
3. Backend creates plan record, inserts items in transaction.
4. User edits order: app sends `PUT /route-plans/<id>` with reordered list; backend updates order numbers.
5. Viewing plan triggers `GET /route-plans/<id>` returning plan + items.
6. Launching navigation uses selected item to open map (client or backend route helper).

### 4.5 Navigation Flow (with Google Maps)
1. From detail/plan, app requests route data either:
   - **Client-only**: App calls Google Directions API directly (requires securing API key via restrictions).
   - **Backend proxy**: App calls `/navigation/route?origin=...&destination=...`.
2. Route data returned (polyline, distance, duration).
3. App renders map overlay, step list, and optionally offers `Intent` to Google Maps app for turn-by-turn.

### 4.6 Data Synchronization & Error Handling
- App centralizes API calls; on network failure shows retry prompts.
- Backend returns meaningful error codes (400 validation errors, 401 unauthorized, 404 not found).
- Token refresh flow uses `POST /auth/refresh` prior to expiry, triggered by HTTP 401 handling middleware on client.

## 5. Cross-Cutting Concerns
- **Security**: HTTPS enforcement, JWT expiration handling, password hashing, input validation, rate limiting (Flask-Limiter optional).
- **Performance**: SQL query optimization, caching layer (Flask-Caching + Redis), lazy loading of heavy fields.
- **Observability**: Logging pipelines, error monitoring (Sentry), basic analytics (user actions tracked via backend).
- **DevOps**: Docker Compose stack (Flask API, MariaDB, Adminer), CI workflows for lint/test/deploy, IaC scripts for staging/production. Compose loads `.env` defaults while overriding database host to the internal `db` service.

## 6. Implementation Milestones (Suggested)
1. **Week 0 Prep**: Environment setup, repo scaffolding (backend + Android), CI pipeline stubs.
2. **Milestone 1**: Database schema finalized, seed data ready, backend auth + restaurant endpoints, Android auth flow connected.
3. **Milestone 2**: Favorites endpoints and UI completed, restaurant search refined, initial maps integration.
4. **Milestone 3**: Route planner backend + UI functional, navigation flow working end-to-end.
5. **Milestone 4**: Comprehensive testing, performance tuning, documentation, deployment automation, release candidate build.

## 7. Deliverables Checklist
- `docs/` updated schemas, API contracts, environment setup instructions.
- Automated tests (pytest, Android unit/UI) with coverage targets.
- Deployment artifacts (Dockerfiles, Helm charts or equivalent, Google Play bundle).
- Monitoring & logging dashboards provisioned.
- User-facing onboarding guide and troubleshooting FAQ.

## 8. Database Seed & Reset Workflow
1. Ensure MariaDB is running locally (e.g., `docker compose up db`) or that the configured `DATABASE_URL` is reachable.
2. Run `python backend/manage.py init_db` to create all tables using SQLAlchemy metadata.
3. Load baseline dataset with `python backend/manage.py seed_db foodmap_db.sql`; the command splits the SQL file and executes statements transactionally.
4. For local development resets, drop the database manually (MariaDB) or remove the SQLite file, then repeat steps 2-3.
5. Keep `foodmap_db.sql` in sync with production data snapshots; regenerate and commit updates whenever the seed dataset changes.

---
*Use this structure as the living blueprint for tracking progress and aligning cross-team responsibilities.*
