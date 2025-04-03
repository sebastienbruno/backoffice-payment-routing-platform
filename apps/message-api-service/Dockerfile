# This image only serves the already-built Spring Boot JAR
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the pre-built JAR from the host
COPY ./dist/apps/message-api-service/*.jar message-api-service.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "message-api-service.jar"]
