# Start from a Maven + JDK image
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Runtime image
FROM eclipse-temurin:21-jdk
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar

# Expose port 8080 for Render
EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
