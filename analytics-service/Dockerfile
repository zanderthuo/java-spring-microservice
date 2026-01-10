# -----------------------------
# Build stage
# -----------------------------
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml ./
RUN mvn -B -DskipTests dependency:go-offline

COPY src ./src
RUN mvn -B -DskipTests clean package

# Normalize jar name
RUN set -eux; \
    ls -lah /app/target; \
    JAR="$(ls -1 /app/target/*.jar | grep -v '\.original$' | head -n 1)"; \
    echo "Using jar: $JAR"; \
    cp "$JAR" /app/app.jar

# -----------------------------
# Run stage
# -----------------------------
FROM eclipse-temurin:21-jre AS runner
WORKDIR /app

COPY --from=builder /app/app.jar /app/app.jar

# Expose HTTP + gRPC (keep whichever you actually use)
EXPOSE 5003

ENTRYPOINT ["java", "-jar", "/app/app.jar"]
