# Build stage
FROM eclipse-temurin:21-alpine AS builder
WORKDIR /app
COPY . .
RUN ./mvnw install -DskipTests

# Run stage
FROM eclipse-temurin:21-alpine
WORKDIR /app
COPY . .
RUN ./mvnw dependency:resolve

EXPOSE 8080
CMD ["./mvnw", "spring-boot:run"]

