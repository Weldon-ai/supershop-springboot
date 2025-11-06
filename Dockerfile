# ---------- Stage 1: Build the JAR file ----------
FROM maven:3.9.6-eclipse-temurin-21 AS builder   # Use Maven with Java 21 to build
WORKDIR /app                                     # Set working directory inside container
COPY pom.xml .                                   # Copy pom.xml first (to cache dependencies)
COPY src ./src                                   # Copy all project source files
RUN mvn clean package -DskipTests                # Build project without running tests


# ---------- Stage 2: Run the built JAR ----------
FROM eclipse-temurin:21-jdk                      # Use a lightweight JDK image for runtime
WORKDIR /app                                     # Set working directory for app

COPY --from=builder /app/target/*.jar app.jar    # Copy built JAR from builder stage

EXPOSE 8080                                      # Expose port 8080 for Render detection

ENV JAVA_OPTS=""                                 # Optional: Environment variable placeholder

CMD ["sh", "-c", "java $JAVA_OPTS -jar app.jar"] # Command to run the Spring Boot app
