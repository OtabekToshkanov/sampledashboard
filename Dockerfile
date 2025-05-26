# Multi-stage build for Spring Boot application
FROM eclipse-temurin:21.0.7_6-jre-ubi9-minimal
WORKDIR /app
COPY ./sampledashboard/target/*.jar /app/app.jar

EXPOSE 8181
ENTRYPOINT ["java", "-jar", "/app/app.jar"]