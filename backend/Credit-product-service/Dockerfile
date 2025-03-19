FROM openjdk:17-jdk-slim AS base
VOLUME /tmp
COPY credit-product-service-api/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
