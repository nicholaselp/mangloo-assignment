FROM openjdk:17-jdk-alpine
LABEL authors="nicholaselp"
ADD target/mangloo-assignment-0.0.1-SNAPSHOT.jar mangloo-app.jar
ENTRYPOINT ["java", "-jar", "mangloo-app.jar"]