FROM openjdk:17-jdk-alpine
VOLUME /tmp
COPY customer-service-api/target/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
