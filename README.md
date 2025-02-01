# SWIFT Code Application

## Project Overview
This application is designed for managing and retrieving SWIFT codes. It provides REST API endpoints to:
- Retrieve SWIFT code details
- Get SWIFT codes by country
- Add new SWIFT codes
- Delete SWIFT codes

The application can be run locally or using **Docker**.

---

## How to Run the Application

### Option 1: Run from GitHub (Manual Setup)
1. Clone the repository:
   ```sh
   git clone https://github.com/szymekpanek/SWIFT-code-exercise.git
   cd SWIFT-code-exercise
   ```
2. Build the project:
   ```sh
   mvn clean package
   ```
3. Run the application:
   ```sh
   mvn spring-boot:run
   ```
4. Access the API at:
   - `GET http://localhost:8080/v1/swift-codes/{swiftCode}`
   - `POST http://localhost:8080/v1/swift-codes/`

---

### Option 2: Run with Docker (Terminal)
1. Pull the Docker image:
   ```sh
   docker pull szymekpanek/swift-code-app
   ```
2. Run the application:
   ```sh
   docker run -p 8080:8080 szymekpanek/swift-code-app
   ```
3. Access the API at:
   - `GET http://localhost:8080/v1/swift-codes/{swiftCode}`
   - `POST http://localhost:8080/v1/swift-codes/`

---

### Option 3: Run with Docker Desktop
1. Open **Docker Desktop** and go to the **Images** section.
2. Pull the image manually:
   ```sh
   docker pull szymekpanek/swift-code-app
   ```
3. Click **Run** in Docker Desktop and map port `8080`.
4. Open a browser and go to:
   - `http://localhost:8080/v1/swift-codes/{swiftCode}`

---

