Traffic Light Controller API
Overview
This project is a Traffic Light Controller API developed as a short, time‑boxed coding kata (3–4 hours). The intention was not to deliver a complete production‑ready solution, but to demonstrate clean design, correctness, testability, and thoughtful trade‑offs under time constraints.
The system models a traffic light controller for a road intersection and exposes a REST API to control and inspect its state.
---
Problem Statement
The system should:
Manage traffic light state changes (`RED`, `YELLOW`, `GREEN`)
Support multiple directions (`NORTH`, `SOUTH`, `EAST`, `WEST`)
Accept commands to:
Change traffic light state
Pause and resume operation
Ensure conflicting directions are never green simultaneously
Provide:
Current traffic light state
Historical state transitions with timestamps
Be safe for concurrent access
Be designed to allow future expansion (e.g., multiple intersections)
---
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
---
Design for Future Expansion
While the current implementation manages a single intersection, the design allows for:
Multiple intersections (mapping intersection IDs to controllers)
Configurable safety rules per intersection
Timed or automated light sequencing
Persistent storage of historical states
These features were intentionally left out due to the kata timebox.
---
What Was Intentionally Out of Scope
Given the limited time window, the following were deliberately not implemented:
Automatic signal sequencing and timers
Database persistence
Advanced HTTP error mapping
Authentication / authorization
Distributed or clustered execution
The focus was on clarity, correctness, and extensibility, rather than feature completeness.
---
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
Summary
This implementation demonstrates:
Clean object‑oriented design
Domain‑driven thinking
Correctness and safety in a state‑driven system
Concurrency awareness
Tests as documentation
Clear trade‑offs under a strict timebox
The emphasis was placed on readable code that communicates intent, in line with the spirit of the kata.
