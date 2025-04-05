# This image only serves the already-built Spring Boot JAR
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Copy the pre-built JAR from the host
COPY ./dist/apps/partner-api-service/*.jar partner-api-service.jar

EXPOSE 8084

ENTRYPOINT ["java", "-jar", "partner-api-service.jar"]
