# Use official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy built JAR file into the container
COPY target/swift-code-intern.jar app.jar

# Copy Excel file into the container
COPY src/main/resources/data/Interns_2025_SWIFT_CODES.xlsx /app/data/Interns_2025_SWIFT_CODES.xlsx

# Expose port 8080
EXPOSE 8080

# Set environment variable to detect Docker
ENV RUNNING_IN_DOCKER=true

# Run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
