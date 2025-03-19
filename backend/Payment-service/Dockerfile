FROM openjdk:17-jdk-slim AS base
VOLUME /tmp
COPY payment-service-api/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
