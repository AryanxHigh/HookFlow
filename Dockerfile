
FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY src ./src

# Retry Maven downloads when Maven Central returns HTTP 429
RUN for attempt in 1 2 3 4 5; do \
    mvn -B -ntp clean package -DskipTests && exit 0; \
    echo "Build failed. Retrying in $((attempt * 20)) seconds..."; \
    sleep $((attempt * 20)); \
    done; \
    exit 1

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 10000

ENTRYPOINT ["java", "-jar", "app.jar"]
