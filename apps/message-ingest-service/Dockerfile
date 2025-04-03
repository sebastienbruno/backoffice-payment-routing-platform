# This image only serves the already-built Spring Boot JAR
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the pre-built JAR from the host
COPY ./apps/message-ingest-service/target/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
