FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY auth-service-api/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
