# Start from Java 17 base image
FROM eclipse-temurin:17-jdk-alpine

# Set working directory
WORKDIR /app

# Install Maven
RUN apk add --no-cache maven

# Copy project files
COPY . .

# Build Spring Boot JAR (skip tests)
RUN mvn clean package -DskipTests

# Copy built JAR file to app.jar
RUN cp target/*.jar app.jar

# Expose app port
EXPOSE 8080

# Start the app
ENTRYPOINT ["java", "-jar", "app.jar"]