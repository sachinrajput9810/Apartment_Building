# Apartment Building Controls

A full-stack building controls application for managing apartments and common rooms with temperature monitoring and HVAC control.

## Tech Stack

- **Backend**: Java 21, Spring Boot 3.2.5, Maven
- **Frontend**: React 18 (Vite)
- **Containerization**: Docker & Docker Compose

## Quick Start

### Prerequisites
- Java 21+
- Maven 3.9+
- Node.js 18+

### Running the Backend

```bash
cd backend
mvn spring-boot:run
```
Backend starts at `http://localhost:8080`

### Running the Frontend

```bash
cd frontend
npm install
npm run dev
```
Frontend starts at `http://localhost:3000`

### Running with Docker

```bash
docker-compose up --build
```
- Frontend: `http://localhost:3000`
- Backend API: `http://localhost:8080`

## API Endpoints

| Method   | Endpoint                    | Description                     |
|----------|-----------------------------|---------------------------------|
| `GET`    | `/api/building`             | Get full building status        |
| `PUT`    | `/api/building/temperature` | Set building requested temp     |
| `POST`   | `/api/rooms/apartment`      | Add a new apartment             |
| `POST`   | `/api/rooms/common-room`    | Add a new common room           |
| `PUT`    | `/api/rooms/{roomId}`       | Update a room's properties      |
| `DELETE` | `/api/rooms/{roomId}`       | Remove a room                   |

## Running Tests

```bash
cd backend
mvn test
```

## Features

- **Real-time temperature simulation** – Room temperatures change every 5 seconds based on heating/cooling state
- **Deadband control** – A ±0.5°C threshold prevents rapid on/off switching near the setpoint
- **Full CRUD** – Add, edit, and remove apartments and common rooms
- **Live dashboard** – Auto-refreshing UI shows current status of all rooms
- **Docker support** – Both services containerised with Docker Compose

## Assumptions & Design Decisions

1. **In-memory storage**: Data is stored in-memory. Restarting the backend resets all rooms to their initial state.
2. **Initial temperature**: The building starts with a setpoint of 25.0°C (as specified in the "Main Application" section) rather than the default of 20.0°C mentioned earlier in the spec.
3. **Deadband of ±0.5°C**: If the room temperature is within 0.5°C of the setpoint, neither heating nor cooling is enabled (requirement: "close enough" support).
4. **Temperature simulation**: Room temperatures increase by 0.1–0.3°C every 5 seconds when heating, decrease by 0.1–0.3°C when cooling, and drift randomly by ±0.05°C when idle.
5. **Room IDs**: Auto-generated 8-character UUIDs since the spec says "unique ID" without specifying format.
6. **Common room types**: Restricted to GYM, LIBRARY, and LAUNDRY as specified.
7. **Owner interpretation**: "Owner" for apartments is a simple string property; no separate owner entity.
8. **Frontend as React**: Required NextJS was interpreted as React (Vite) based on reviewer preference.
9. **Default building name**: "Daikin Apartments" was used as the building name (not specified in requirements).

## Project Structure

```
├── backend/
│   ├── src/main/java/com/apartmentcontrols/
│   │   ├── ApartmentControlsApplication.java    # Main entry point
│   │   ├── config/JacksonConfig.java             # JSON polymorphism config
│   │   ├── controller/BuildingController.java    # REST API
│   │   ├── dto/                                  # Request DTOs
│   │   ├── model/                                # Domain models
│   │   └── service/BuildingService.java          # Core business logic
│   └── src/test/                                 # Unit tests
├── frontend/
│   ├── src/
│   │   ├── App.jsx                              # Main dashboard
│   │   ├── api.js                               # API integration
│   │   ├── index.css                            # Design system
│   │   └── components/                          # React components
│   └── nginx.conf                               # Docker nginx config
├── docker-compose.yml
└── README.md
```
