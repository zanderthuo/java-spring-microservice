# syntax=docker/dockerfile:1.6

# -----------------------------
# Build stage
# -----------------------------
FROM maven:3.9.9-eclipse-temurin-21 AS builder
WORKDIR /app

COPY ../pom.xml ./

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -DskipTests \
    -Dmaven.wagon.http.retryHandler.count=5 \
    -Dmaven.wagon.httpconnectionManager.ttlSeconds=60 \
    -Dmaven.wagon.http.pool=false \
    dependency:go-offline

COPY ../src ./src

RUN --mount=type=cache,target=/root/.m2 \
    mvn -B -DskipTests clean package

# ✅ Normalize output jar name to /app/app.jar (avoid guessing artifactId/version)
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

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
