# ── Stage 1: Build ────────────────────────────────────────────────────
# Use Maven + Java 17 to build the jar
FROM maven:3.9-eclipse-temurin-17 AS build

WORKDIR /app

# Copy pom.xml first — Docker caches dependencies so rebuild is faster
COPY pom.xml .
RUN mvn dependency:go-offline -q

# Copy source code and build
COPY src ./src
RUN mvn clean package -DskipTests -q

# ── Stage 2: Run ──────────────────────────────────────────────────────
# Use lightweight Java 17 image — no Maven needed at runtime
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy only the built jar from Stage 1
COPY --from=build /app/target/portfolio-api-1.0.0.jar app.jar

# Render sets PORT dynamically — expose it
EXPOSE 8080

# Start the Spring Boot app
ENTRYPOINT ["java", "-jar", "app.jar"]
