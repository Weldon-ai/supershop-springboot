# Use official Java runtime as base image
FROM eclipse-temurin:17-jdk-alpine

# Set the working directory inside the container
WORKDIR /app

# Copy your Spring Boot jar file into the container
COPY target/*.jar app.jar

# Expose the port your app runs on
EXPOSE 8080

# Command to run your app
ENTRYPOINT ["java", "-jar", "app.jar"]
