What Has Been Implemented
1. Domain Model
The core domain is intentionally simple and expressive:
Direction – NORTH, SOUTH, EAST, WEST
LightColor – RED, YELLOW, GREEN
TrafficLightState
Immutable snapshot of all directions at a point in time
Automatically records a timestamp
ConflictingGreenException
Domain exception enforcing safety rules
The domain layer is framework‑agnostic and contains no Spring or infrastructure code.
---
2. Traffic Light Controller
The `TrafficLightController` encapsulates all business rules:
Applies state changes
Enforces safety constraints (no conflicting greens)
Maintains a history of all state transitions
Supports pause / resume operations
Concurrency considerations:
All state changes are protected by a lock (`ReentrantLock`)
Ensures correctness under concurrent API access
Uses immutable state objects for clarity and safety
---
3. REST API
A thin Spring Boot REST layer exposes the system’s functionality.
Available Endpoints
Method	Endpoint	Description
POST	`/api/traffic/state`	Change traffic light state
POST	`/api/traffic/pause`	Pause the controller
POST	`/api/traffic/resume`	Resume operation
GET	`/api/traffic/state`	Fetch current state
GET	`/api/traffic/history`	Fetch state change history
The API layer contains no business logic and delegates all decisions to the controller.
---
4. Testing Strategy
Tests were written to serve as executable documentation.
Controller tests validate:
Valid state transitions
Rejection of conflicting green directions
API integration tests validate:
REST endpoints using `MockMvc`
Real HTTP request/response behavior
Safety rule enforcement via the API
Test names are intentionally descriptive and focus on behavior rather than implementation details.

How To Run
Build & Test
```bash
mvn clean test
```
Run the Application
```bash
mvn spring-boot:run
```
The API will be available at:
```
http://localhost:8080
```
---
